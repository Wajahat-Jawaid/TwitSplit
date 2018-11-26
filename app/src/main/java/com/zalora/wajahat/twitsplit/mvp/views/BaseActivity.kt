package com.zalora.wajahat.twitsplit.mvp.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.TwitSplitApp
import com.zalora.wajahat.twitsplit.Utils
import com.zalora.wajahat.twitsplit.data.SharedPrefs
import com.zalora.wajahat.twitsplit.mvp.interactors.BaseInteractor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var context: Context? = null
    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var prefs: SharedPrefs
    @Inject
    lateinit var baseInteractor: BaseInteractor
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        TwitSplitApp.instance?.component?.inject(this)
        context = this
        super.onCreate(savedInstanceState)
    }

    fun hasInternet(): Boolean {
        return if (Utils.hasInternet())
            true
        else {
            showToast(R.string.no_internet)
            false
        }
    }

    fun showToast(@StringRes msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showSnack(@StringRes msg: Int) {
        val viewGroup = (this
            .findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        Snackbar.make(viewGroup, msg, Snackbar.LENGTH_SHORT).show()
    }

    fun finishWithMessage(@StringRes msg: Int) {
        showToast(msg)
        finish()
    }

    fun switchActivityForResult(activity: Class<out BaseActivity>, reqCode: Int) {
        startActivityForResult(Intent(context, activity), reqCode)
    }

    fun switchActivity(activity: Class<out BaseActivity>, flags: Int) {
        val i = Intent(context, activity)
        i.flags = flags
        startActivity(i)
    }

    fun switchActivity(activity: Class<out BaseActivity>) {
        val i = Intent(context, activity)
        startActivity(i)
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
        baseInteractor.onDestroy()
        super.onDestroy()
    }

    fun showDialogWithDismissCallback(
        @StringRes message: Int,
        positiveText: Int,
        positiveCall: MaterialDialog.SingleButtonCallback
    ) {
        MaterialDialog.Builder(this)
            .content(message)
            .positiveText(positiveText)
            .onPositive(positiveCall)
            .negativeText(android.R.string.cancel)
            .onNegative { dialog, _ -> dialog.dismiss() }
            .show()
    }

    fun showMaterialDialog(
        title: String,
        @StringRes message: Int,
        positiveText: Int,
        positiveCall: MaterialDialog.SingleButtonCallback,
        negativeText: Int,
        negativeCall: MaterialDialog.SingleButtonCallback
    ) {
        MaterialDialog.Builder(this)
            .title(title)
            .content(message)
            .positiveText(positiveText)
            .onPositive(positiveCall)
            .negativeText(negativeText)
            .onNegative(negativeCall)
            .show()
    }

    companion object {
        const val TAG = "BaseActivity"
    }
}