package com.zalora.wajahat.twitsplit.mvp.interactors

import android.support.annotation.StringRes
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.zalora.wajahat.twitsplit.Constants
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.TwitSplitApp
import com.zalora.wajahat.twitsplit.models.MainInterfaceTweets
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import kotlin.collections.ArrayList

class MainInteractor : BaseInteractor() {

    private var tweetsFetched = false
    private var mTweets = ArrayList<MainInterfaceTweets>()
    private val prettyTime = PrettyTime()
    private var fullName: String? = null
    private var username: String? = null

    init {
        fullName = getProfileInfo().fullName
        username = getProfileInfo().username
    }

    interface OnTweetsFetchedListener {

        fun onTweetsFetched(tweets: ArrayList<MainInterfaceTweets>)
        fun onTweetsFailed(@StringRes error: Int)
    }

    private fun setDataChangeListener(username: String, listener: OnTweetsFetchedListener) {
        TwitSplitApp.instance
                ?.getFirebaseDatabase(username)
                ?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        if (mTweets.size > 0) {
                            listener.onTweetsFetched(mTweets)
                            if (!tweetsFetched) tweetsFetched = true
                        } else listener.onTweetsFailed(R.string.no_tweets)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                    }
                })
    }

    fun fetchUserTweets(listener: OnTweetsFetchedListener) {
        TwitSplitApp.instance
                ?.getFirebaseDatabase(username)
                ?.addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                        val map = dataSnapshot.value as (Map<*, *>)
                        val tweet = map[Constants.TWEET].toString()
                        val duration = prettyTime.format(Date())
                        if (!tweetsFetched && username != null)
                            setDataChangeListener(username!!, listener)
                        mTweets.add(MainInterfaceTweets(fullName, username, duration, tweet))
                    }

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                    override fun onChildRemoved(p0: DataSnapshot) {}
                    override fun onCancelled(p0: DatabaseError) {}
                })
    }

    companion object {
        private const val TAG = "AMainIteractor"
    }
}