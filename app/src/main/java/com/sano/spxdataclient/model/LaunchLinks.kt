package com.sano.spxdataclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sano.spxdataclient.database.model.BdLaunchLinks
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaunchLinks(
        @SerializedName("mission_patch")
        val missionPatch: String?,

        @SerializedName("reddit_campaign")
        val redditCampaign: String?,

        @SerializedName("reddit_launch")
        val redditLaunch: String?,

        @SerializedName("reddit_media")
        val redditMedia: String?,

        @SerializedName("presskit")
        val presskit: String?,

        @SerializedName("article_link")
        val articleLink: String?,

        @SerializedName("wikipedia")
        val wikipedia: String?,

        @SerializedName("video_link")
        val videoLink: String?) : Parcelable {

    fun getLinks(): List<LaunchLink> {
        val list = ArrayList<LaunchLink>()

        redditCampaign?.let { list.add(LaunchLink(REDDIT, redditCampaign)) }
        redditLaunch?.let { list.add(LaunchLink(REDDIT, redditLaunch)) }
        redditMedia?.let { list.add(LaunchLink(REDDIT, redditMedia)) }
        presskit?.let { list.add(LaunchLink(PDF, presskit)) }
        articleLink?.let { list.add(LaunchLink(LINK, articleLink)) }
        wikipedia?.let { list.add(LaunchLink(WIKIPEDIA, wikipedia)) }
        videoLink?.let { list.add(LaunchLink(VIDEO, videoLink)) }

        return list
    }

    fun toBdLaunchLinks() =
            BdLaunchLinks(0,
                    missionPatch,
                    redditCampaign,
                    redditLaunch,
                    redditMedia,
                    presskit,
                    articleLink,
                    wikipedia,
                    videoLink)
}