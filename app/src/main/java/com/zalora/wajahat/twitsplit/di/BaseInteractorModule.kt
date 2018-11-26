package com.zalora.wajahat.twitsplit.di

import com.zalora.wajahat.twitsplit.mvp.interactors.BaseInteractor
import dagger.Module
import dagger.Provides

/** Dependency for the base MODELs of MVP */
@Module
class BaseInteractorModule {

    @AppScope
    @Provides
    fun baseInteractor(): BaseInteractor {
        return BaseInteractor()
    }
}