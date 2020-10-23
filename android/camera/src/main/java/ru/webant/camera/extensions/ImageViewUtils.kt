package ru.webant.camera.extensions

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat

fun ImageView.setImageDrawable(context: Context, drawableResource: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(context, drawableResource))
}