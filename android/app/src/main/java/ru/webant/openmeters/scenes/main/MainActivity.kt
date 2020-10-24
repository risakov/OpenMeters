package ru.webant.openmeters.scenes.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.webant.camera.CameraActivity.Companion.RESULT_CODE_SUCCESS
import ru.webant.openmeters.R
import ru.webant.openmeters.extensions.setIsVisible

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navHostFragment) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val intent = Intent(this, CameraActivity::class.java)
//        startActivityForResult(intent, CAMERA_REQUEST_CODE)
        hideStatusBar()
        setupBottomNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_CODE_SUCCESS) {
            Toast.makeText(this, "${data!!.data}", Toast.LENGTH_SHORT).show()
        }
    }

    fun changeBottomNavigationBarVisibility(state: Boolean) {
        bottomNavigationView.setIsVisible(state)
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

    private fun setupBottomNavigation() {
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun hideStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 1010
    }
}