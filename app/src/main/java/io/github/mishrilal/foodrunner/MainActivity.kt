package io.github.mishrilal.foodrunner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var txtMobileNumber: TextView
    lateinit var txtPassword: TextView
    lateinit var btnLogOut: Button

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        setContentView(R.layout.activity_main)

        title = "Login Details"
        txtMobileNumber = findViewById(R.id.txtMobileNumber)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogOut = findViewById(R.id.btnLogOut)

        if(intent != null){
            txtMobileNumber.text = intent.getStringExtra("MobileNumber")
            txtPassword.text = intent.getStringExtra("Password")
        }

        val logoutIntent = Intent(this@MainActivity, LoginActivity::class.java)
        btnLogOut.setOnClickListener {
            startActivity(logoutIntent)
            sharedPreferences.edit().clear().apply()
            finish()
        }

    }
}