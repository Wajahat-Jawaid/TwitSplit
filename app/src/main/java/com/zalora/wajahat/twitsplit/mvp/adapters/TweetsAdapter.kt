package com.zalora.wajahat.twitsplit.mvp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.models.MainInterfaceTweets

class TweetsAdapter(private var mTweets: ArrayList<MainInterfaceTweets>)
    : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        // Inflating impl with the extension function of kotlin
        return ViewHolder(parent.inflate(R.layout.row_item_tweet))
    }

    override fun getItemCount(): Int {
        return mTweets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mTweets[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /** Bind impl with the tweet
         * @param tweet Tweet message model
         * */
        fun bind(tweet: MainInterfaceTweets) {
            // Full name and username though could be passed through the parameter in
            // the constructor instead of fetching every time from the tweet model. But
            // in actual application, we get tweets from many people, so considering that real
            // scenario, I've set it from tweet model
            itemView.findViewById<TextView>(R.id.tv_full_name).text = tweet.fullName
            itemView.findViewById<TextView>(R.id.tv_username).text = tweet.username
            itemView.findViewById<TextView>(R.id.tv_duration).text = tweet.duration
            itemView.findViewById<TextView>(R.id.tv_tweet).text = tweet.tweet
        }
    }

    /** Kotlin Extension function to inflate views */
    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    companion object {
        private const val TAG = "ATweetsAdapter"
    }
}