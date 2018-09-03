package com.sano.spxdataclient.launchDetails

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.model.*

class LinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val mLogo: ImageView = itemView.findViewById(R.id.iv_logo)
    private val mText: TextView = itemView.findViewById(R.id.tv_link)

    fun bind(link: LaunchLink) {
        mLogo.setImageResource(getLinkImageRes(link.type))
        mText.text = link.url
    }

    @DrawableRes
    fun getLinkImageRes(@LinkType type: Int): Int =
            when (type) {
                REDDIT -> R.drawable.reddit_logo
                PDF -> R.drawable.pdf_logo
                LINK -> R.drawable.web_logo
                WIKIPEDIA -> R.drawable.wiki_logo
                VIDEO -> R.drawable.youtube_logo
                else -> R.drawable.web_logo
            }
}