package com.zalora.wajahat.twitsplit.mvp.custom

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/** This view is currently used in the @link SendActivity.
 * The EditText interferes the BACK key event and overrides the Android default behavior
 * to close the EditText only instead of closing EditText along with finishing off the activity*/
class AutoBackEditText(context: Context?, attributes: AttributeSet)
    : EditText(context, attributes) {

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val inputMngr = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputMngr.hideSoftInputFromWindow(this.windowToken, 0)
        }
        return false
    }
}