package com.zalora.wajahat.twitsplit.mvp.presenters

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.zalora.wajahat.twitsplit.TwitSplitApp
import javax.inject.Inject

/** Base class for all the MVP presenters */
open class BasePresenter {

    @Inject
    lateinit var context: Context

    init {
        TwitSplitApp.instance?.component?.inject(this)
    }

    fun showToast(@StringRes msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}