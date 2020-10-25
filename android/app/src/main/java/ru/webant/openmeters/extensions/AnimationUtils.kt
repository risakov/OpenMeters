package ru.webant.openmeters.extensions

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView

fun ImageView.rotateInfiniteAnimation() {
    val rotateAnimation = RotateAnimation(
        0f, 360f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )

    rotateAnimation.interpolator = LinearInterpolator()
    rotateAnimation.duration = 750
    rotateAnimation.repeatCount = Animation.INFINITE

    this.startAnimation(rotateAnimation)
}