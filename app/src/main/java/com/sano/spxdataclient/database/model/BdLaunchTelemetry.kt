package com.sano.spxdataclient.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sano.spxdataclient.model.LaunchTelemetry

@Entity
data class BdLaunchTelemetry(
        @PrimaryKey(autoGenerate = true)
        val id: Long,

        @ColumnInfo(name = "flight_club")
        val flightClub: String?) {

    fun toLaunchTelemetry() = LaunchTelemetry(flightClub)
}