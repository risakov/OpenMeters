package ru.webant.openmeters.scenes.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.value_history.IndicatorHistoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, IndicatorHistoryFragment())
                .commit()
        }

//        val intent = Intent(this, CameraActivity::class.java)
//        startActivity(intent)
        hideStatusBar()
    }

    fun setUpActionBar(view: View?) {
        if (view == null) {
            supportActionBar?.hide()
            return
        }
        supportActionBar?.show()
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowCustomEnabled(true)
            setCustomView(
                view,
                ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            )
            elevation = 0f
        }
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
    }

    private fun hideStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}