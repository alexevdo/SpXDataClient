package com.sano.spxdataclient

import com.sano.spxdataclient.database.SpxDao
import com.sano.spxdataclient.database.model.BdLaunch
import com.sano.spxdataclient.model.Launch
import com.sano.spxdataclient.utils.DateFormatManager

class Storage(private val spxDao: SpxDao) {

    fun overwriteLaunches(launches: List<Launch>) {
        spxDao.clearLaunchLinksTable()
        spxDao.clearLaunchTelemetryTable()
        spxDao.clearRocketTable()
        spxDao.clearLaunchTable()

        val linksIds = spxDao.insertLaunchLinks(launches.map { it.links.toBdLaunchLinks() })
        val launchTelemetryIds = spxDao.insertLaunchTelemetry(launches.map { it.telemetry.toBdLaunchTelemetry() })
        val rocketIds = spxDao.insertRocket(launches.map { it.rocket.toBdRocket() })

        spxDao.insertLaunches(launches.mapIndexed { index, launch ->
            launch.toBdLaunch(rocketIds[index], launchTelemetryIds[index], linksIds[index])
        })
    }

    fun getAllLaunches() = spxDao.getLaunches().linkWithData()

    fun getLatestLaunch() =
            spxDao.getLaunches()
                    .filter { !it.isUpcoming }
                    .sortedBy { DateFormatManager.getDate(it.timestamp) }
                    .last()
                    .run { listOf(this) }
                    .linkWithData()

    fun getNextLaunch() =
            spxDao.getLaunches()
                    .filter { it.isUpcoming }
                    .sortedBy { DateFormatManager.getDate(it.timestamp) }
                    .first()
                    .run { listOf(this) }
                    .linkWithData()

    fun getPastLaunches() =
            spxDao.getLaunches()
                    .filter { !it.isUpcoming }
                    .linkWithData()

    fun getUpcomingLaunches() =
            spxDao.getLaunches()
                    .filter { it.isUpcoming }
                    .linkWithData()

    interface StorageOwner {
        fun obtainStorage(): Storage
    }

    private fun List<BdLaunch>.linkWithData(): List<Launch> {
        val launchLinks = spxDao.getLaunchLinks(map { it.linksId })
        val launchTelemetries = spxDao.getLaunchTelemetries(map { it.telemetryId })
        val rockets = spxDao.getRockets(map { it.rocketId })

        return map { bdLaunch ->
            bdLaunch.toLaunch(
                    rockets.find { it.id == bdLaunch.rocketId }!!.toRocket(),
                    launchTelemetries.find { it.id == bdLaunch.telemetryId }!!.toLaunchTelemetry(),
                    launchLinks.find { it.id == bdLaunch.linksId }!!.toLaunchLinks())
        }
    }
}

