package com.sano.spxdataclient.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Launch(
        @SerializedName("flight_number") val int: Int,
        @SerializedName("mission_name") val missionName: String,
        @SerializedName("launch_year") val launchYear: Int,
        @SerializedName("launch_date_local") val timestamp: String,
        @SerializedName("launch_success") val isSuccess: Boolean,
        @SerializedName("details") val details: String,
        @SerializedName("upcoming") val isUpcoming: Boolean,
        @SerializedName("rocket") val rocket: Rocket,
        @SerializedName("telemetry") val telemetry: LaunchTelemetry,
        @SerializedName("links") val links: LaunchLinks
)