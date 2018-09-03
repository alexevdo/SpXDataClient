package com.sano.spxdataclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sano.spxdataclient.database.model.BdLaunch
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Launch(
        @SerializedName("flight_number")
        val flightNumber: Long,

        @SerializedName("mission_name")
        val missionName: String,

        @SerializedName("launch_year")
        val launchYear: Int,

        @SerializedName("launch_date_local")
        val timestamp: String,

        @SerializedName("launch_success")
        val isSuccess: Boolean,

        @SerializedName("details")
        val details: String?,

        @SerializedName("upcoming")
        val isUpcoming: Boolean,

        @SerializedName("rocket")
        val rocket: Rocket,

        @SerializedName("telemetry")
        val telemetry: LaunchTelemetry,

        @SerializedName("links")
        val links: LaunchLinks) : Parcelable {

    fun toBdLaunch(rocketId: Long, telemetryId: Long, linksId: Long) =
            BdLaunch(flightNumber,
                    missionName,
                    launchYear,
                    timestamp,
                    isSuccess,
                    details,
                    isUpcoming,
                    rocketId,
                    telemetryId,
                    linksId)
}