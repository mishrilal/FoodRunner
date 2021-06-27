package io.github.mishrilal.foodrunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegisterNewActivity : AppCompatActivity() {

    lateinit var txtFullName: TextView
    lateinit var txtMobileNumber: TextView
    lateinit var txtEmail: TextView
    lateinit var txtAddress: TextView
    lateinit var txtPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new)

        title = "Registration Details"

        txtFullName = findViewById(R.id.txtFullName)
        txtMobileNumber = findViewById(R.id.txtMobileNumber)
        txtEmail = findViewById(R.id.txtEmail)
        txtAddress = findViewById(R.id.txtAddress)
        txtPassword = findViewById(R.id.txtPassword)

        if(intent != null) {
            txtFullName.text = intent.getStringExtra("Name")
            txtMobileNumber.text = intent.getStringExtra("MobileNumber")
            txtEmail.text = intent.getStringExtra("Email")
            txtAddress.text = intent.getStringExtra("Address")
            txtPassword.text = intent.getStringExtra("Password")
        }
    }
}