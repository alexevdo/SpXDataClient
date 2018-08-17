package com.sano.spxdataclient.model

import com.google.gson.annotations.SerializedName

data class LaunchTelemetry(
        @SerializedName("flight_club") val flightClub: String
)