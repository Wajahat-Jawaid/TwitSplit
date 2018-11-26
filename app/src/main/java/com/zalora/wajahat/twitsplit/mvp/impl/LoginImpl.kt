package com.zalora.wajahat.twitsplit.mvp.impl

import android.support.annotation.StringRes

interface LoginImpl {

    fun getUsername(): String
    fun showUsernameError(@StringRes msg: Int)
    fun getFullName(): String
    fun showFullNameError(@StringRes msg: Int)
    fun onLoginSuccess()
}