package ru.webant.openmeters.scenes.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.webant.openmeters.R
import ru.webant.openmeters.extensions.isCameraPermissionGranted
import ru.webant.openmeters.scenes.camera.CameraFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CameraFragment())
                .commit()
        }
    }
}