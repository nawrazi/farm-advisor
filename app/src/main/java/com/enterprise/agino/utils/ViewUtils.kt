package com.enterprise.agino.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


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

fun showErrorSnackBar(message: String, rootView: View) {
    Snackbar.make(
        rootView,
        message,
        Snackbar.LENGTH_LONG
    ).show()
}

fun showSnackBar(message: String, rootView: View) {
    Snackbar.make(
        rootView,
        message,
        Snackbar.LENGTH_LONG
    ).show()
}

fun showSuccessSnackBar(message: String, rootView: View) {
    Snackbar.make(
        rootView,
        message,
        Snackbar.LENGTH_LONG
    ).show()
}