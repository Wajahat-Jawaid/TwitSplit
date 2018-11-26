package com.zalora.wajahat.twitsplit.mvp.impl

import android.support.annotation.StringRes

/** MVP Contract for the @link SendActivity */
interface SendImpl {

    fun showValidationError(@StringRes error : Int)
    fun getTweet() : String
    fun onPostTweet(chunksCount: Int)
    fun onTweetCancelled(@StringRes error: Int)
    fun onPerWordCharLimitExceeded(tweet: String, word: String)
    fun onCharLimitExceededWordErased(updatedTweet: String)
}