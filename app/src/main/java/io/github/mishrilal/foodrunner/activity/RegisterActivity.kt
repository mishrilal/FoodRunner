package io.github.mishrilal.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.github.mishrilal.foodrunner.R

class RegisterActivity : AppCompatActivity() {

    lateinit var etFullName: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etEmail: EditText
    lateinit var etAddress: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        title = "Register Yourself"

        etFullName = findViewById(R.id.etFullName)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        var intent = Intent(this@RegisterActivity, RegisterNewActivity::class.java)
        btnRegister.setOnClickListener {
            val name = etFullName.text.toString()
            val mobileNumber = etMobileNumber.text.toString()
            val email = etEmail.text.toString()
            val address = etAddress.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (password.length <= 4) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password is too Small, Try Different Password",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password == confirmPassword) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Registered Successfully",
                    Toast.LENGTH_LONG
                ).show()

                intent.putExtra("Name", name)
                intent.putExtra("MobileNumber", mobileNumber)
                intent.putExtra("Email", email)
                intent.putExtra("Address", address)
                intent.putExtra("Password", password)

                startActivity(intent)
                finish()
            } else if (password != confirmPassword) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password did not match, Try again",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Something went Wrong, Try again later",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
}