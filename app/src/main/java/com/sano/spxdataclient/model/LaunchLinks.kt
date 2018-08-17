package com.sano.spxdataclient.model

import android.os.Parcelable
import android.support.annotation.IntDef
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

const val REDDIT: Int = 0
const val PDF = 1
const val LINK = 2
const val WIKIPEDIA = 3
const val VIDEO = 4

@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
@IntDef(REDDIT, PDF, LINK, WIKIPEDIA, VIDEO)
annotation class LinkType

@Parcelize
data class LaunchLinks(
        @SerializedName("mission_patch") val missionPatch: String?,
        @SerializedName("reddit_campaign") val redditCampaign: String?,
        @SerializedName("reddit_launch") val redditLaunch: String?,
        @SerializedName("reddit_media") val redditMedia: String?,
        @SerializedName("presskit") val presskit: String?,
        @SerializedName("article_link") val articleLink: String?,
        @SerializedName("wikipedia") val wikipedia: String?,
        @SerializedName("video_link") val videoLink: String?) : Parcelable {

    fun getLinks(): List<Pair<@LinkType Int, String>> {
        val list = ArrayList<Pair<@LinkType Int, String>>()

        redditCampaign?.let { list.add(Pair(REDDIT, redditCampaign)) }
        redditLaunch?.let { list.add(Pair(REDDIT, redditLaunch)) }
        redditMedia?.let { list.add(Pair(REDDIT, redditMedia)) }
        presskit?.let { list.add(Pair(PDF, presskit)) }
        articleLink?.let { list.add(Pair(LINK, articleLink)) }
        wikipedia?.let { list.add(Pair(WIKIPEDIA, wikipedia)) }
        videoLink?.let { list.add(Pair(VIDEO, videoLink)) }

        return list
    }
}