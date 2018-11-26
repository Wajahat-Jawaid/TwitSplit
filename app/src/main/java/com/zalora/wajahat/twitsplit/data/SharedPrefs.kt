package com.zalora.wajahat.twitsplit.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils

class SharedPrefs(context: Context) {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    /** Insert String value */
    fun insert(key: String, value: String?) {
        if (!TextUtils.isEmpty(value)) {
            val editor = getEditor()
            editor.putString(key, value)
            editor.apply()
        }
    }

    fun getString(key: String): String? {
        return prefs.getString(key, "bc")
    }

    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }
}