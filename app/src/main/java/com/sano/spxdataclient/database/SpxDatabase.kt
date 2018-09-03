package com.sano.spxdataclient.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sano.spxdataclient.database.model.BdLaunch
import com.sano.spxdataclient.database.model.BdLaunchLinks
import com.sano.spxdataclient.database.model.BdLaunchTelemetry
import com.sano.spxdataclient.database.model.BdRocket

@Database(entities = [BdLaunch::class, BdLaunchLinks::class, BdLaunchTelemetry::class, BdRocket::class], version = 2)
abstract class SpxDatabase : RoomDatabase() {
    abstract fun getSpxDao(): SpxDao
}
