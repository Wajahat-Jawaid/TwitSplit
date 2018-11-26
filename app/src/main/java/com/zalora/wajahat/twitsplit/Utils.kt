package com.zalora.wajahat.twitsplit

import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class Utils {

    /**
     * Return String from the given edittext
     *
     * @param et EditText whose value has to be extracted
     * @return String et text
     */
    fun EToS(et: EditText): String {
        return et.text.toString().trim { it <= ' ' }
    }

    fun hideKeyboard(et: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
    }

    companion object {

        fun hasInternet(): Boolean {
            val cm =
                    TwitSplitApp.instance?.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE)
                            as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}