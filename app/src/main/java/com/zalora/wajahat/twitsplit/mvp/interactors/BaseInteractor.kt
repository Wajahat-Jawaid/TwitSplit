package com.zalora.wajahat.twitsplit.mvp.interactors

import com.zalora.wajahat.twitsplit.Constants
import com.zalora.wajahat.twitsplit.TwitSplitApp
import com.zalora.wajahat.twitsplit.data.SharedPrefs
import com.zalora.wajahat.twitsplit.models.Profile
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseInteractor {

    @Inject
    lateinit var prefs: SharedPrefs
    val compositeDisposable = CompositeDisposable()

    init {
        TwitSplitApp.instance?.component?.inject(this)
    }

    fun onDestroy() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }

    fun getProfileInfo(): Profile {
        return Profile(prefs.getString(Constants.FULL_NAME),
                prefs.getString(Constants.USERNAME))
    }
}