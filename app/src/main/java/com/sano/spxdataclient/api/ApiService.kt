package com.sano.spxdataclient.api

import com.sano.spxdataclient.model.Launch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("launches/latest")
    fun getLatestLaunch(): Single<Launch>

    @GET("launches/next")
    fun getNextLaunch(): Single<Launch>

    @GET("launches")
    fun getPastLaunches(): Single<List<Launch>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Single<List<Launch>>

    @GET("launches/all")
    fun getAllLaunches(): Single<List<Launch>>

    @GET("launches")
    fun getQueryLaunches(@Query("launch_year") year: Int,
                         @Query("rocket_id") rocketId: String,
                         @Query("core_reuse") isCoreReuse: Boolean,
                         @Query("core_serial") coreSerial: String)
}