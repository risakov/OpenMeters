package ru.webant.openmeters.scenes.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import ru.webant.openmeters.R
import ru.webant.openmeters.extensions.rotateInfiniteAnimation
import ru.webant.openmeters.local_adress.LocalAddressActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        loaderImageView.rotateInfiniteAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            run {
                val mainIntent = Intent(this@SplashActivity, LocalAddressActivity::class.java)
                this@SplashActivity.startActivity(mainIntent)
                this@SplashActivity.finish()
            }
        }, SPLASH_DISPLAY_LENGTH)
    }


    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 1000L
    }
}