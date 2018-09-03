package com.sano.spxdataclient.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.model.LaunchLinks
import com.sano.spxdataclient.model.LaunchTelemetry
import com.sano.spxdataclient.model.Rocket

@Entity(foreignKeys = [
    ForeignKey(entity = BdRocket::class, parentColumns = ["id"], childColumns = ["rocket_id"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = BdLaunchTelemetry::class, parentColumns = ["id"], childColumns = ["telemetry_id"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = BdLaunchLinks::class, parentColumns = ["id"], childColumns = ["links_id"], onDelete = ForeignKey.CASCADE)])
data class BdLaunch(
        @PrimaryKey
        @ColumnInfo(name = "flightNumber")
        val flightNumber: Long,

        @ColumnInfo(name = "mission_name")
        val missionName: String,

        @ColumnInfo(name = "launch_year")
        val launchYear: Int,

        @ColumnInfo(name = "launch_date_local")
        val timestamp: String,

        @ColumnInfo(name = "launch_success")
        val isSuccess: Boolean,

        @ColumnInfo(name = "details")
        val details: String?,

        @ColumnInfo(name = "upcoming")
        val isUpcoming: Boolean,

        @ColumnInfo(name = "rocket_id")
        val rocketId: Long,

        @ColumnInfo(name = "telemetry_id")
        val telemetryId: Long,

        @ColumnInfo(name = "links_id")
        val linksId: Long) {

    fun toLaunch(rocket: Rocket, telemetry: LaunchTelemetry, links: LaunchLinks) =
            Launch(flightNumber,
                    missionName,
                    launchYear,
                    timestamp,
                    isSuccess,
                    details,
                    isUpcoming,
                    rocket,
                    telemetry,
                    links)
}