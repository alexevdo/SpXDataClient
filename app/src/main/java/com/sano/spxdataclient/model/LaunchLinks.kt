package com.sano.spxdataclient.model

import com.google.gson.annotations.SerializedName

data class LaunchLinks (
        @SerializedName("mission_patch") val missionPatch: String,
        @SerializedName("reddit_campaign") val redditCampaign: String,
        @SerializedName("reddit_launch") val redditLaunch: String,
        @SerializedName("reddit_media") val redditMedia: String,
        @SerializedName("presskit") val presskit: String,
        @SerializedName("article_link") val articleLink: String,
        @SerializedName("wikipedia") val wikipedia: String,
        @SerializedName("video_link") val videoLink: String
)