package com.zalora.wajahat.twitsplit.mvp.views

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.models.MainInterfaceTweets
import com.zalora.wajahat.twitsplit.mvp.adapters.TweetsAdapter
import com.zalora.wajahat.twitsplit.mvp.impl.MainImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.MainInteractor
import com.zalora.wajahat.twitsplit.mvp.presenters.MainPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainImpl {

    private val presenter = MainPresenter(this, MainInteractor())
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (hasInternet()) {
            presenter.fetchTweets()
        } else {
            hideProgressBar()
            showErrorMsg(R.string.no_internet)
        }
        setListeners()
    }

    private fun setListeners() {
        subscriptions.add(RxView.clicks(fab_new_tweet).subscribe {
            switchActivityForResult(SendActivity::class.java, SEND_TWEET_REQ_CODE)
        })
    }

    override fun setRecyclerView(tweets: ArrayList<MainInterfaceTweets>) {
        val recyclerView = recycler_view
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recyclerView!!.layoutManager = layoutManager
        recyclerView.adapter = TweetsAdapter(tweets)
        recyclerView.scrollToPosition(tweets.size)
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun showErrorMsg(@StringRes error: Int) {
        tv_error.text = getString(error)
        tv_error.visibility = View.VISIBLE
    }

    override fun hideErrorMsg() {
        if (tv_error.visibility == View.VISIBLE)
            tv_error.visibility = View.GONE
    }

    override fun onDestroy() {
        if (!subscriptions.isDisposed) subscriptions.dispose()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "AMainActivity"
        private const val SEND_TWEET_REQ_CODE = 101
    }
}