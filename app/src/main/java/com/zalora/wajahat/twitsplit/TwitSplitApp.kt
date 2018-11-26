package com.zalora.wajahat.twitsplit

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zalora.wajahat.twitsplit.di.AppComponent
import com.zalora.wajahat.twitsplit.di.ContextModule
import com.zalora.wajahat.twitsplit.di.DaggerAppComponent

class TwitSplitApp : Application() {

    var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
        component = DaggerAppComponent.builder().contextModule(ContextModule(this)).build()
    }

    companion object {

        var instance: TwitSplitApp? = null
    }

    /** Get the firebase tweets node reference
     * Node structure is "Users" --> "Username" --> "Tweets" --> "{tweets}"
     * */
    internal fun getFirebaseDatabase(username: String?): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        return database.getReference(Constants.USERS)
                .child(username!!)
                .child(Constants.TWEETS)
    }
}