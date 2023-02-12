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