package com.zalora.wajahat.twitsplit.di

import com.zalora.wajahat.twitsplit.mvp.interactors.BaseInteractor
import com.zalora.wajahat.twitsplit.mvp.presenters.BasePresenter
import com.zalora.wajahat.twitsplit.mvp.views.BaseActivity
import dagger.Component

@AppScope
@Component(
        modules = [ContextModule::class, SharedPrefsModule::class, UtilsModule::class,
            BaseInteractorModule::class]
)
interface AppComponent {

    fun inject(baseActivity: BaseActivity)
    fun inject(baseActivity: BaseInteractor)
    fun inject(basePresenter: BasePresenter)
}