package ru.webant.openmeters.scenes.main

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.value_history.ValueHistoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ValueHistoryFragment())
                .commit()
        }

//        val intent = Intent(this, CameraActivity::class.java)
//        startActivity(intent)
        hideStatusBar()
    }

    private fun hideStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}