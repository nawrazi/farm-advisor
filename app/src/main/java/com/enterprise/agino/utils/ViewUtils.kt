package com.enterprise.agino.utils

import android.view.View


fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.hideAndDisable() {
    visibility = View.INVISIBLE
    isEnabled = false
}

fun View.gone() {
    visibility = View.GONE
}


fun View.show() {
    visibility = View.VISIBLE
}

fun View.showAndEnable() {
    visibility = View.VISIBLE
    isEnabled = true
}