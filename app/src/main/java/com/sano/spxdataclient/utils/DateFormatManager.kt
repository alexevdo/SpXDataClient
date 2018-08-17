package com.sano.spxdataclient.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatManager {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
    val dateFormat = SimpleDateFormat.getDateInstance()
    val timeFormat = SimpleDateFormat.getTimeInstance()

    // "2006-03-25T10:30:00+12:00"
    fun formatDate(stamp: String): String {
        val date = getDate(stamp)
        return "${dateFormat.format(date)} ${timeFormat.format(date)}"
    }

    fun getDate(stamp: String): Date = originalFormat.parse(stamp)
}
