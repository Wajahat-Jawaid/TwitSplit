package com.zalora.wajahat.twitsplit.mvp.interactors

import com.zalora.wajahat.twitsplit.Constants

class LoginInteractor : BaseInteractor() {

    interface OnLoginFinishedListener {
        fun onDataSavedToPrefs()
    }

    fun saveDataToPrefs(fullName: String, username: String, listener: OnLoginFinishedListener) {
        prefs.insert(Constants.FULL_NAME, fullName)
        prefs.insert(Constants.USERNAME, username)
        listener.onDataSavedToPrefs()
    }
}