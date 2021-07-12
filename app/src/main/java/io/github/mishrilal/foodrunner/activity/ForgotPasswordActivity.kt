package io.github.mishrilal.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import io.github.mishrilal.foodrunner.R

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var etForgotPassword: EditText
    lateinit var etEmail: EditText
    lateinit var btnReset: Button
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        /*This method is also user created to setup the toolbar*/
        setupToolbar()

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

        toolbar.setNavigationOnClickListener{
            startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
            finishAffinity()
        }

    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Reset Password"
    }

}