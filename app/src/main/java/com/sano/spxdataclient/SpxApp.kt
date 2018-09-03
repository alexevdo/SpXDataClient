package com.sano.spxdataclient

import android.app.Application
import android.arch.persistence.room.Room
import com.sano.spxdataclient.database.SpxDatabase

class SpxApp : Application() {

    lateinit var mStorage: Storage
        private set

    override fun onCreate() {
        super.onCreate()

        val database = Room.databaseBuilder(this, SpxDatabase::class.java, "spx_database")
                .fallbackToDestructiveMigration()
                .build()

        mStorage = Storage(database.getSpxDao())
    }
}