package com.zalora.wajahat.twitsplit

import android.content.Intent

object Constants {

    const val FULL_NAME = "full_name"

    const val HAS_SESSION = "has_session"

    const val ID = "id"

    const val TIMESTAMP = "time_stamp"
    const val TWEET = "tweet"
    const val CHUNKS_COUNT = "chunks_count"
    const val TWEETS = "tweets"

    const val USERS = "users"
    const val USERNAME = "username"

    const val FLAG_NEW_TASK_LAUNCHER = Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_NEW_TASK
}