package com.zalora.wajahat.twitsplit.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPrefs(context: Context) {

    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    /** Insert String value */
    fun insert(key: String, value: String?) {
        getEditor().putString(key, value)
        getEditor().apply()
    }

    fun getString(key: String): String? {
        return prefs.getString(key, "")
    }
}