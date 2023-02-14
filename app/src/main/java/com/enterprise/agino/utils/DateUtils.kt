package com.enterprise.agino.utils

import java.text.SimpleDateFormat
import java.util.*

fun millisToDateString(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    val calendar = Calendar.getInstance().also {
        it.timeInMillis = millis
    }
    return formatter.format(calendar.time)
}

fun utcToDateString(date: String): String {
    val result = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).parse(date)
    return SimpleDateFormat("E", Locale.US).format(result!!)
}