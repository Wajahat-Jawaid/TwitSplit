package com.zalora.wajahat.twitsplit.models

/** Data class for the tweets holder on the main interface */
data class MainInterfaceTweets(val fullName: String?, val username: String?,
                               val duration: String?, val tweet: String?) {

    var id: String? = null
}