package com.zalora.wajahat.twitsplit.mvp.presenters

import android.support.annotation.StringRes
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.mvp.impl.SendImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.SendInteractor

class SendPresenter(private val impl: SendImpl, private val model: SendInteractor) : BasePresenter(),
    SendInteractor.OnTweetActionListener {

    fun sendTweet() {
        if (impl.getTweet().isEmpty()) {
            impl.showValidationError(R.string.tweet_empty)
            return
        }
        model.sendTweet(impl.getTweet(), this)
    }

    override fun onTweetSuccess(chunksCount: Int) {
        impl.onPostTweet(chunksCount)
    }

    override fun onTweetCancelled(@StringRes error: Int) {
        showToast(error)
    }

    /** Method from the SendInteractor$OnTweetActionListener which is called when
     * a word with length greater than 50 characters is found in the tweet message*/
    override fun onPerWordCharLimitExceeded(tweet: String, word: String) {
        impl.onPerWordCharLimitExceeded(tweet, word)
    }

    /** On the user action, remove the 50 characters limit exceeded word from the tweet
     * and set that updated tweet text to EditText */
    fun eraseCharLimitExceededWordAndRetry(tweet: String, word: String) {
        val erased = model.eraseLimitExceededWord(tweet, word)
        impl.onCharLimitExceededWordErased(erased)
    }
}