package com.zalora.wajahat.twitsplit.mvp.views

import android.os.Bundle
import android.text.TextUtils
import com.jakewharton.rxbinding2.view.RxView
import com.zalora.wajahat.twitsplit.Constants
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.mvp.impl.LoginImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.LoginInteractor
import com.zalora.wajahat.twitsplit.mvp.presenters.LoginPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginImpl {

    private val presenter = LoginPresenter(this, LoginInteractor())
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState != null)
            showDataFromBundle(savedInstanceState)
        subscriptions.add(RxView.clicks(b_continue).subscribe { presenter.onLoginClicked() })
    }

    private fun showDataFromBundle(savedInstanceState: Bundle) {
        if (!TextUtils.isEmpty(savedInstanceState.getString(Constants.FULL_NAME)))
            et_full_name.setText(savedInstanceState.getString(Constants.FULL_NAME))
        if (!TextUtils.isEmpty(savedInstanceState.getString(Constants.USERNAME)))
            et_username.setText(savedInstanceState.getString(Constants.USERNAME))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(Constants.FULL_NAME, utils.EToS(et_full_name))
        outState?.putString(Constants.USERNAME, utils.EToS(et_username))
    }

    override fun getUsername(): String {
        return utils.EToS(et_username)
    }

    override fun showUsernameError(msg: Int) {
        showToast(msg)
    }

    override fun getFullName(): String {
        return utils.EToS(et_full_name)
    }

    override fun showFullNameError(msg: Int) {
        showToast(msg)
    }

    override fun onLoginSuccess() {
        switchActivity(MainActivity::class.java, Constants.FLAG_NEW_TASK_LAUNCHER)
    }

    override fun onDestroy() {
        if (!subscriptions.isDisposed) subscriptions.dispose()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "ALoginActivity"
    }
}