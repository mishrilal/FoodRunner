package io.github.mishrilal.foodrunner.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import io.github.mishrilal.foodrunner.R
import io.github.mishrilal.foodrunner.util.ConnectionManager
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    lateinit var etFullName: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etEmail: EditText
    lateinit var etAddress: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button
    private lateinit var toolbar: Toolbar
    lateinit var sharedPreferences: SharedPreferences

    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    var mobilePattern = "[6-9][0-9]{9}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        setContentView(R.layout.activity_register)

        setupToolbar()

        etFullName = findViewById(R.id.etFullName)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        var intent = Intent(this@RegisterActivity, MainActivity::class.java)
        btnRegister.setOnClickListener {
            val name = etFullName.text.toString()
            val mobileNumber = etMobileNumber.text.toString()
            val email = etEmail.text.toString()
            val address = etAddress.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

//            if (password.length <= 4) {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    "Password is too Small, Try Different Password",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else if (password == confirmPassword) {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    "Registered Successfully",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                intent.putExtra("Name", name)
//                intent.putExtra("MobileNumber", mobileNumber)
//                intent.putExtra("Email", email)
//                intent.putExtra("Address", address)
//                intent.putExtra("Password", password)
//
//                startActivity(intent)
//                finish()
//            } else if (password != confirmPassword) {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    "Password did not match, Try again",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    "Something went Wrong, Try again later",
//                    Toast.LENGTH_LONG
//                ).show()
//                finish()
//            }


            if (etFullName.text.toString().isEmpty())
                Toast.makeText(this@RegisterActivity, "Enter Name", Toast.LENGTH_LONG).show()
            else if (etEmail.text.toString().isEmpty())
                Toast.makeText(this@RegisterActivity, "Enter Email Id", Toast.LENGTH_LONG)
                    .show()
            else if (etMobileNumber.text.toString().isEmpty())
                Toast.makeText(this@RegisterActivity, "Enter Mobile Number", Toast.LENGTH_LONG)
                    .show()
            else if (etAddress.text.toString().isEmpty())
                Toast.makeText(
                    this@RegisterActivity,
                    "Enter Delivery Address",
                    Toast.LENGTH_LONG
                )
                    .show()
            else if (etPassword.text.toString().isEmpty())
                Toast.makeText(this@RegisterActivity, "Enter Password", Toast.LENGTH_LONG)
                    .show()
            else if (etPassword.text.toString() != etConfirmPassword.text.toString())
                Toast.makeText(
                    this@RegisterActivity,
                    "Passwords doesn't match. Please try again!",
                    Toast.LENGTH_LONG
                ).show()
            else if (!etEmail.text.toString().trim().matches(emailPattern.toRegex()))
                Toast.makeText(
                    this@RegisterActivity,
                    "Enter a valid Email Id",
                    Toast.LENGTH_LONG
                )
                    .show()
            else if (!etMobileNumber.text.toString().trim().matches(mobilePattern.toRegex()))
                Toast.makeText(
                    this@RegisterActivity,
                    "Enter a valid Mobile number",
                    Toast.LENGTH_LONG
                ).show()
            else if (etPassword.length() < 4) {
                Toast.makeText(this@RegisterActivity, "Weak Password", Toast.LENGTH_LONG)
                    .show()
            } else {
                sendRequest(
                    etFullName.text.toString(),
                    etMobileNumber.text.toString(),
                    etAddress.text.toString(),
                    etPassword.text.toString(),
                    etEmail.text.toString()
                )

                Toast.makeText(
                    this@RegisterActivity,
                    "Successfully Registered",
                    Toast.LENGTH_SHORT
                )
                    .show()
                startActivity(intent)
                finish()
            }
        }


        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finishAffinity()
        }
    }

    private fun sendRequest(
        name: String,
        phone: String,
        address: String,
        password: String,
        email: String
    ) {

        val url = "http://13.235.250.119/v2/register/fetch_result"
        val queue = Volley.newRequestQueue(this@RegisterActivity)
        val jsonParams = JSONObject()
        jsonParams.put("name", name)
        jsonParams.put("mobile_number", phone)
        jsonParams.put("password", password)
        jsonParams.put("address", address)
        jsonParams.put("email", email)

        if (ConnectionManager().isNetworkAvailable(this@RegisterActivity)) {
            val jsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                url,
                jsonParams,
                Response.Listener {

                    try {
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {

                            val response = data.getJSONObject("data")
                            sharedPreferences.edit()
                                .putString("user_id", response.getString("user_id"))
                                .apply()
                            sharedPreferences.edit()
                                .putString("user_name", response.getString("name"))
                                .apply()
                            sharedPreferences.edit().putString(
                                "user_mobile_number",
                                response.getString("mobile_number")
                            ).apply()
                            sharedPreferences.edit()
                                .putString(
                                    "user_address",
                                    response.getString("address")
                                )
                                .apply()
                            sharedPreferences.edit()
                                .putString("user_email", response.getString("email"))
                                .apply()

                            savePreferences()
                        } else {
//                            rlRegister.visibility = View.VISIBLE
                            //progressBar.visibility=View.INVISIBLE
                            Toast.makeText(
                                this@RegisterActivity,
                                "Some error occurred! in save",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
//                        rlRegister.visibility = View.VISIBLE
                        //progressBar.visibility=View.INVISIBLE
                        e.printStackTrace()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(this@RegisterActivity, "Volley Error!", Toast.LENGTH_SHORT)
                        .show()
//                    rlRegister.visibility = View.VISIBLE
                    //progressBar.visibility=View.VISIBLE
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        } else {
            val dialog = AlertDialog.Builder(this@RegisterActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                this.finish()

            }
            dialog.setNegativeButton("Exit") { text, listener ->

                ActivityCompat.finishAffinity(this@RegisterActivity)
            }
            dialog.create()
            dialog.show()
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Register Yourself"
    }

    fun savePreferences() {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }

}