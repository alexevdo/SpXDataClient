package com.sano.spxdataclient.api

import com.google.gson.Gson
import com.sano.spacexlaunches.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

object ApiUtils {

    val NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException::class,
            SocketTimeoutException::class,
            ConnectException::class
    )

    private val retrofit: Retrofit

    init {
        val gson = Gson()
        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}