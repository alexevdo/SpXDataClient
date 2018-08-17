package com.sano.spxdataclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rocket(
        @SerializedName("rocket_id") val rocketId: String,
        @SerializedName("rocket_name") val rocketName: String,
        @SerializedName("rocket_type") val rocketType: String): Parcelable