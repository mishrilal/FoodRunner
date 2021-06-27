package io.github.mishrilal.foodrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class ForgotNextActivity : AppCompatActivity() {

    lateinit var txtMobileNumber: TextView
    lateinit var txtEmail: TextView
    var mobileNumber: String? = "No Mobile Number"
    var email: String? = "No Email Address"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_next)

        title = "Forgot Password Data"
        txtMobileNumber = findViewById(R.id.txtMobileNumber)
        txtEmail = findViewById(R.id.txtEmail)

        if(intent != null){
            mobileNumber = intent.getStringExtra("MobileNumber")
            email = intent.getStringExtra("Email")
            txtMobileNumber.text = mobileNumber
            txtEmail.text = email
        }



    }
}