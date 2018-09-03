package com.sano.spxdataclient.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sano.spxdataclient.database.model.BdLaunch
import com.sano.spxdataclient.database.model.BdLaunchLinks
import com.sano.spxdataclient.database.model.BdLaunchTelemetry
import com.sano.spxdataclient.database.model.BdRocket

@Dao
interface SpxDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunches(launches: List<BdLaunch>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunchLinks(launchLinks: List<BdLaunchLinks>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunchTelemetry(launchTelemetry: List<BdLaunchTelemetry?>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRocket(rockets: List<BdRocket?>): List<Long>

    @Query("select * from bdlaunch")
    fun getLaunches(): List<BdLaunch>

    @Query("select * from bdlaunchlinks where id IN(:launchIds)")
    fun getLaunchLinks(launchIds: List<Long>): List<BdLaunchLinks>

    @Query("select * from bdlaunchtelemetry where id IN(:launchIds)")
    fun getLaunchTelemetries(launchIds: List<Long>): List<BdLaunchTelemetry>

    @Query("select * from bdrocket where id IN(:launchIds)")
    fun getRockets(launchIds: List<Long>): List<BdRocket>

    @Query("delete from bdlaunch")
    fun clearLaunchTable()

    @Query("delete from bdlaunchlinks")
    fun clearLaunchLinksTable()

    @Query("delete from bdlaunchtelemetry")
    fun clearLaunchTelemetryTable()

    @Query("delete from bdrocket")
    fun clearRocketTable()
}