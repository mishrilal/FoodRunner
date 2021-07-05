package io.github.mishrilal.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.github.mishrilal.foodrunner.R

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var etForgotPassword: EditText
    lateinit var etEmail: EditText
    lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        title = "Reset Password"

        etForgotPassword = findViewById(R.id.etMobileNumber)
        etEmail = findViewById(R.id.etEmail)
        btnReset = findViewById(R.id.btnReset)
        var intent = Intent(this@ForgotPasswordActivity, ForgotNextActivity::class.java)
        btnReset.setOnClickListener {

            val mobileNumber = etForgotPassword.text.toString()
            val email = etEmail.text.toString()
            println(mobileNumber)
            println(email)
            intent.putExtra("MobileNumber", mobileNumber)
            intent.putExtra("Email", email)
            startActivity(intent)
            finish()
        }

    }



}