package com.sano.spxdataclient.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatManager {
    private val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
    private val dateFormat = SimpleDateFormat.getDateInstance()
    private val timeFormat = SimpleDateFormat.getTimeInstance()

    fun formatDate(stamp: String): String {
        val date = getDate(stamp)
        return "${dateFormat.format(date)} ${timeFormat.format(date)}"
    }

    fun getDate(stamp: String): Date = originalFormat.parse(stamp)
}
