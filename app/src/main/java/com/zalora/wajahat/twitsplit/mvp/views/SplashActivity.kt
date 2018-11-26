package com.zalora.wajahat.twitsplit.mvp.views

import android.os.Bundle
import android.util.Log
import com.zalora.wajahat.twitsplit.Constants


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs.insert(Constants.FULL_NAME, "Wajahat Jawaid")
        prefs.insert(Constants.USERNAME, "wajahat")
        switchActivity(MainActivity::class.java, Constants.FLAG_NEW_TASK_LAUNCHER)
        val density = resources.displayMetrics.density
        Log.d(TAG, "density: $density")
//        setContentView(R.layout.act_splash)
//        val delay = if (prefs.getBool(Constants.HAS_SESSION)) 0 else 1000
//        Log.d(TAG, "delay: $delay")
//        Handler().postDelayed({
//            if (delay > 0) switchActivity(RegisterActivity::class.java)
//            else switchActivity(ActDashboard::class.java)
//        }, delay.toLong())
    }

    companion object {
        private const val TAG = "AActSplash"
    }
}