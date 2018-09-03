package com.sano.spxdataclient.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sano.spxdataclient.model.LaunchLinks

@Entity
data class BdLaunchLinks(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long,

        @ColumnInfo(name = "mission_patch")
        val missionPatch: String?,

        @ColumnInfo(name = "reddit_campaign")
        val redditCampaign: String?,

        @ColumnInfo(name = "reddit_launch")
        val redditLaunch: String?,

        @ColumnInfo(name = "reddit_media")
        val redditMedia: String?,

        @ColumnInfo(name = "presskit")
        val presskit: String?,

        @ColumnInfo(name = "article_link")
        val articleLink: String?,

        @ColumnInfo(name = "wikipedia")
        val wikipedia: String?,

        @ColumnInfo(name = "video_link")
        val videoLink: String?) {

        fun toLaunchLinks() =
                LaunchLinks(missionPatch,
                        redditCampaign,
                        redditLaunch,
                        redditMedia,
                        presskit,
                        articleLink,
                        wikipedia,
                        videoLink)
}