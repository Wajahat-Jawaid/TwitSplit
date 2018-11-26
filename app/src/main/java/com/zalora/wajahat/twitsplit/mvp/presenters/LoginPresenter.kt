package com.zalora.wajahat.twitsplit.mvp.presenters

import android.util.Log
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.mvp.impl.LoginImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.LoginInteractor

class LoginPresenter(private val impl: LoginImpl, private val model: LoginInteractor) :
        BasePresenter(), LoginInteractor.OnLoginFinishedListener {

    fun onLoginClicked() {
        if (impl.getFullName().isEmpty()) {
            impl.showFullNameError(R.string.full_name_empty)
            return
        }
        if (impl.getUsername().isEmpty()) {
            impl.showUsernameError(R.string.username_empty)
            return
        }
        Log.d(TAG, "Saving data to prefs now")
        model.saveDataToPrefs(impl.getFullName(), impl.getUsername(), this)
    }

    override fun onDataSavedToPrefs() {
        impl.onLoginSuccess()
    }

    companion object {
        private const val TAG = "LoginPresenter"
    }
}