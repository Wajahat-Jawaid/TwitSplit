package com.zalora.wajahat.twitsplit.di

import android.content.Context
import com.zalora.wajahat.twitsplit.data.SharedPrefs
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class SharedPrefsModule {

    @AppScope
    @Provides
    fun sharedPrefs(context: Context): SharedPrefs {
        return SharedPrefs(context)
    }
}