package com.zalora.wajahat.twitsplit.mvp.interactors

import android.support.annotation.StringRes
import com.zalora.wajahat.twitsplit.Constants
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.TwitSplitApp
import java.util.*

class SendInteractor : BaseInteractor() {

    private val messageSize = 50
    /** Considering the part indicator to be the part of the chunk length, it's necessary
     * to know the length of part indicator so as to comply with the chunk size. Messages
     * with chunks in 10s will have different length as compared to the ones with chunks count
     * in units. This threshold size indicates that if the message length is greater than 414,
     * chunks will be in 10s.
     * 414 is calculated by, if chunks are max 9, then 9x4 = 36.
     * 9 chunks size will be 9x50 = 450.
     * When part indicator included, then 450-36 = 414*/
    private val unitSizePartIndicatorLen = 414
    private val space = ' '

    private var username: String? = null

    init {
        username = getProfileInfo().username
    }

    interface OnTweetActionListener {
        fun onTweetSuccess(chunksCount: Int)
        fun onTweetCancelled(@StringRes error: Int)
        fun onPerWordCharLimitExceeded(tweet: String, word: String)
    }

    fun sendTweet(tweet: String, listener: OnTweetActionListener) {
        if (tweet.length > MAX_LIMIT)
            listener.onTweetCancelled(R.string.max_tweet_limit)
        val chunks = splitTweet(tweet.trim(), listener)
        if (chunks != null && chunks.size > 0) {
            val databaseReference = TwitSplitApp.instance?.getFirebaseDatabase(username)
            val map = HashMap<String, String>()
            var anyFailure = false
            val count = chunks.size
            for (i in 0 until count) {
                map[Constants.TIMESTAMP] = System.currentTimeMillis().toString()
                map[Constants.TWEET] = chunks[i]
                val task = databaseReference?.push()?.setValue(map)
            }
            listener.onTweetSuccess(count)
        }
    }

    fun splitTweet(msg: String, listener: OnTweetActionListener): ArrayList<String>? {
        val chunks = ArrayList<String>()
        if (!msg.isEmpty()) {
            // If the message length is already less than the #messageSize, then simply
            // return as it is
            if (msg.length <= messageSize) {
                chunks.add(msg)
                return chunks
            }
            // Multi-chunks message
            val words = msg.split(space)
            val msgLength = msg.length
            val chunksInTensDigits = msgLength > unitSizePartIndicatorLen
            val wordsCount = words.size
            val partIndicatorLen = if (!chunksInTensDigits) 4 else 5
            var i = 0
            var chunkRemLen = getChunkInitCapacity(partIndicatorLen)
            val chunkBuilder = StringBuilder()
            while (i < wordsCount) {
                // isEmpty check is because for the single chunk, we need to perform decrement
                // in chunkRemLen once. After first iteration, #chunkBuilder will not be empty
                // and hence the following if condition will get false leading to the
                // surety of it getting called only once
                if (chunksInTensDigits && chunks.size > 9 && chunkBuilder.isEmpty()) {
                    --chunkRemLen // As the numerator will also have it's size incremented
                    // with 1 due to getting in 10s
                }
                val word = words[i]
                if (isWordGreaterThan50Letters(word)) {
                    listener.onPerWordCharLimitExceeded(msg, word)
                    return null
                }
                if (chunkRemLen - word.length >= 0) {
                    // Message limit not full yet and this word is eligible to be added
                    // in the current message
                    chunkBuilder.append(word)
                    chunkRemLen -= word.length
                    if (chunkRemLen < messageSize) {
                        chunkBuilder.append(space) // If still space is left, then add the blank
                        // space
                        --chunkRemLen // 1 is added in length for the blank space
                    }
                    if (i < wordsCount - 1)
                        i++
                    else {
                        chunks.add(chunkBuilder.toString())
                        break
                    }
                } else {
                    // When the chunk limit is reached #messageSize, add that to the chunks list
                    // and reset that
                    chunks.add(chunkBuilder.toString())
                    chunkBuilder.setLength(0)
                    chunkRemLen = getChunkInitCapacity(partIndicatorLen)
                }
            }
        }

        // Creating a new array
        val chunksWithPartInd = ArrayList<String>()

        for (i in 0 until chunks.size) {
            chunksWithPartInd.add("${i + 1}/${chunks.size} ${chunks[i].trim()}")
        }

        return chunksWithPartInd
    }

    private fun getChunkInitCapacity(partIndicatorLen: Int): Int {
        return messageSize - partIndicatorLen // 50 - 4 = 46 OR 5- - 5 = 45
    }

    fun isWordGreaterThan50Letters(word: String): Boolean {
        return word.length > messageSize
    }

    fun eraseLimitExceededWord(tweet: String, word: String): String {
        if (tweet.contains(word)) {
            return tweet.replace(word, "")
        }

        return tweet
    }

    companion object {
        private const val TAG = "ASendInterator"
        // No logic to put this figure, but as following the practice to avoid scams, such
        // limit is put
        private const val MAX_LIMIT = 2000
    }
}