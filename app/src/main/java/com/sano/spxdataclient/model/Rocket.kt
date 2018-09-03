package com.sano.spxdataclient.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sano.spxdataclient.database.model.BdRocket
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Rocket(
        @SerializedName("rocket_id")
        val rocketId: String,

        @SerializedName("rocket_name")
        val rocketName: String,

        @SerializedName("rocket_type")
        val rocketType: String): Parcelable {

        fun toBdRocket() = BdRocket(0, rocketId, rocketName, rocketType)
}