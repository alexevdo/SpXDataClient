package com.sano.spxdataclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sano.spxdataclient.database.model.BdLaunchTelemetry
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaunchTelemetry(
        @SerializedName("flight_club")
        val flightClub: String?): Parcelable {

        fun toBdLaunchTelemetry() = BdLaunchTelemetry(0, flightClub)
}