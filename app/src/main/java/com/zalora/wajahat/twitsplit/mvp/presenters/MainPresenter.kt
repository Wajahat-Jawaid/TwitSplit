package com.zalora.wajahat.twitsplit.mvp.presenters

import com.zalora.wajahat.twitsplit.models.MainInterfaceTweets
import com.zalora.wajahat.twitsplit.mvp.impl.MainImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.MainInteractor

class MainPresenter(private val impl: MainImpl, private val model: MainInteractor) :
        BasePresenter(), MainInteractor.OnTweetsFetchedListener {

    fun fetchTweets() {
        model.fetchUserTweets(this)
    }

    override fun onTweetsFetched(tweets: ArrayList<MainInterfaceTweets>) {
        impl.hideProgressBar()
        impl.setRecyclerView(tweets)
    }

    override fun onTweetsFailed(error: Int) {
        impl.hideProgressBar()
        impl.showErrorMsg(error)
    }
}