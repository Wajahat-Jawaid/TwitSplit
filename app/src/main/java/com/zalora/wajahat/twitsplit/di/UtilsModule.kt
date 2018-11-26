package com.zalora.wajahat.twitsplit.di

import com.zalora.wajahat.twitsplit.Utils
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @AppScope
    @Provides
    fun utils(): Utils {
        return Utils()
    }
}