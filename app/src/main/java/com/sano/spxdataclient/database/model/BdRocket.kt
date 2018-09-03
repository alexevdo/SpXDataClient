package com.sano.spxdataclient.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sano.spxdataclient.model.Rocket

@Entity
data class BdRocket(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long,

        @ColumnInfo(name = "rocket_id")
        val rocketId: String,

        @ColumnInfo(name = "rocket_name")
        val rocketName: String,

        @ColumnInfo(name = "rocket_type")
        val rocketType: String) {

        fun toRocket() = Rocket(rocketId, rocketName, rocketType)
}