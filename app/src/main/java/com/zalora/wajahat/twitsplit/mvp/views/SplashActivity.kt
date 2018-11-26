package com.zalora.wajahat.twitsplit.mvp.views

import android.os.Bundle
import com.zalora.wajahat.twitsplit.Constants

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (prefs.contains(Constants.FULL_NAME) && prefs.contains(Constants.USERNAME))
            switchActivity(MainActivity::class.java)
        else switchActivity(LoginActivity::class.java)
    }

    companion object {
        private const val TAG = "AActSplash"
    }
}