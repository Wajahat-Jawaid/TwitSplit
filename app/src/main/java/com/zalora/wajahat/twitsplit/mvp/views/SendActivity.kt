package com.zalora.wajahat.twitsplit.mvp.views

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.jakewharton.rxbinding2.view.RxView
import com.zalora.wajahat.twitsplit.Constants
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.mvp.impl.SendImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.SendInteractor
import com.zalora.wajahat.twitsplit.mvp.presenters.SendPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.send_activity.*

class SendActivity : BaseActivity(), SendImpl {

    /** Disposables manager */
    private val subscriptions = CompositeDisposable()
    private val presenter = SendPresenter(this, SendInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_activity)
        if (!TextUtils.isEmpty(savedInstanceState?.getString(Constants.TWEET)))
            et_tweet.setText(savedInstanceState?.getString(Constants.TWEET))
        setListeners()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(Constants.TWEET, utils.EToS(et_tweet))
    }

    private fun setListeners() {
        subscriptions.add(RxView.clicks(ib_clear).subscribe {
            if (!hasDraft()) {
                Handler().post { utils.hideKeyboard(et_tweet, this) }
                finish()
            } else {
                // Call to finish the activity
                val finishCallback =
                    MaterialDialog.SingleButtonCallback { _, _ -> finish() }
                showDialogWithDismissCallback(
                    R.string.tweet_in_progress,
                    R.string.dismiss, finishCallback
                )
            }
        })
        subscriptions.add(RxView.clicks(b_tweet).subscribe {
            //            if (!TextUtils.isEmpty(utils.EToS(et_tweet)))
            if (hasInternet()) {
                presenter.sendTweet()
            } else {
                showSnack(R.string.no_internet)
            }
        })
    }

    private fun hasDraft(): Boolean {
        return !TextUtils.isEmpty(utils.EToS(et_tweet))
    }

    override fun onPostTweet(chunksCount: Int) {
        finishWithMessage(R.string.tweet_success)
    }

    override fun onTweetCancelled(error: Int) {
    }

    override fun onCharLimitExceededWordErased(updatedTweet: String) {
        et_tweet.setText(updatedTweet)
    }

    override fun onPerWordCharLimitExceeded(tweet: String, word: String) {
        // Call to dismiss the dialog
        val okCall = MaterialDialog.SingleButtonCallback { dialog, _ -> dialog.dismiss() }
        // Call to begin action to find and erase the limit exceeded word
        val eraseWordCall =
            MaterialDialog.SingleButtonCallback { _, _ ->
                presenter.eraseCharLimitExceededWordAndRetry(tweet, word)
            }
        showMaterialDialog(
            word, R.string.word_limit_exceeded,
            android.R.string.ok, okCall,
            R.string.erase_this_word, eraseWordCall
        )
    }

    override fun getTweet(): String {
        return utils.EToS(et_tweet)
    }

    override fun showValidationError(error: Int) {
        showToast(error)
    }

    companion object {
        private const val TAG = "AMainActivity"
    }
}