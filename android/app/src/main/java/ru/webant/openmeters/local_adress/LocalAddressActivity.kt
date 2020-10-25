package ru.webant.openmeters.local_adress

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_local_address.*
import ru.webant.gateway.constants.ApiConstants.API_URL
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.main.MainActivity

class LocalAddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_address)
        supportActionBar?.hide()
        setListeners()
    }

    private fun setListeners() {
        readyButton.setOnClickListener {
            val address = localAddressEditText.text.toString()
            if (isAddressValid(address)) {
                API_URL = localAddressEditText.text.toString()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Введите корректный url!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isAddressValid(address: String): Boolean {
        return address.contains(Regex("(^https:\\/\\/|^http:\\/\\/)(\\d{3}|\\d{2})\\.(\\d{3}|\\d{2})\\.(\\d{3}|\\d{2})\\.(\\d{3}|\\d{2})"))
    }
}