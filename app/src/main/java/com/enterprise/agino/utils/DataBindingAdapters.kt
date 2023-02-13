package com.enterprise.agino.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tomtom.sdk.location.GeoPoint
import java.text.DateFormat
import java.util.*

@BindingAdapter("date", "dateFormatStyle", requireAll = true)
fun TextView.addFormattedDate(date: Date?, dateFormatStyle: Int?) {
    if (date == null || dateFormatStyle == null) {
        return
    }
    text = DateFormat.getDateInstance(dateFormatStyle).format(date)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("android:text")
fun TextView.addLocation(location: GeoPoint?) {
    if (location == null) {
        return
    }

    text = ("%.6f".format(location.latitude) + ", " + "%.6f".format(location.longitude))
}