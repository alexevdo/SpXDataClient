package com.sano.spxdataclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaunchTelemetry(
        @SerializedName("flight_club") val flightClub: String): Parcelable