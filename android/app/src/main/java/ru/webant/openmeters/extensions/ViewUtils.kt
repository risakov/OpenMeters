package ru.webant.openmeters.extensions

import android.view.View

fun View.setIsVisible(isVisible: Boolean) {
    if (isVisible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}