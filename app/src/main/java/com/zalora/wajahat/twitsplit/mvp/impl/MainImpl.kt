package com.zalora.wajahat.twitsplit.mvp.impl

import android.support.annotation.StringRes
import com.zalora.wajahat.twitsplit.models.MainInterfaceTweets

interface MainImpl {

    fun setRecyclerView(tweets: ArrayList<MainInterfaceTweets>)
    fun hideProgressBar()
    fun showErrorMsg(@StringRes error: Int)
    fun hideErrorMsg()
}