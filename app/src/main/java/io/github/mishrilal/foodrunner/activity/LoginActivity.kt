package io.github.mishrilal.foodrunner.activity
// TODO: Add progress Bar for login btn
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import io.github.mishrilal.foodrunner.R
import io.github.mishrilal.foodrunner.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var txtForgotPassword: TextView
    lateinit var txtRegister: TextView
    lateinit var btnLogin: Button
    lateinit var progressBarLogin: ProgressBar

    private val validMobileNumber = "1234567890"
    private val validPassword = "password"

    var mobilePattern = "[6-9][0-9]{9}"

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        setContentView(R.layout.activity_login)

        if (isLoggedIn) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        title = "Food Runner"

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtRegister = findViewById(R.id.txtRegister)
        btnLogin = findViewById(R.id.btnLogin)
        progressBarLogin= findViewById(R.id.progressBarLogin)

        btnLogin.setOnClickListener {
            progressBarLogin.visibility= View.VISIBLE
            etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

            val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)

            if (validations(etMobileNumber.text.toString(), etPassword.text.toString())) {

                if (ConnectionManager().isNetworkAvailable(this@LoginActivity)) {

                    val queue = Volley.newRequestQueue(this@LoginActivity)
                    val url = "http://13.235.250.119/v2/login/fetch_result/"
                    val jsonParams = JSONObject()
                    jsonParams.put("mobile_number", mobileNumber)
                    jsonParams.put("password", password)

                    val jsonObjectRequest =
                        object : JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            jsonParams,
                            Response.Listener {
                                try {
                                    val data = it.getJSONObject("data")
                                    val success = data.getBoolean("success")
                                    if (success) {
                                        btnLogin.isEnabled = false
                                        btnLogin.isClickable = false

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
                                        progressBarLogin.visibility= View.GONE
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        progressBarLogin.visibility= View.GONE
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Invalid Credentials",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            },
                            Response.ErrorListener {
                                progressBarLogin.visibility= View.GONE
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Volley error occurred",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                    progressBarLogin.visibility= View.GONE
                    val dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle("Error")
                    dialog.setMessage("Internet Connection Found")
                    dialog.setPositiveButton("Open Settings") { text, listener ->
                        val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                        startActivity(settingsIntent)
                        this.finish()

                    }
                    dialog.setNegativeButton("Exit") { text, listener ->

                        ActivityCompat.finishAffinity(this@LoginActivity)
                    }
                    dialog.create()
                    dialog.show()
                }
            }
        }

        txtForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }

        txtRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

    }

    fun savePreferences() {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun validations(phone: String, password: String): Boolean {

        if (phone.isEmpty() && password.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Enter Credentials", Toast.LENGTH_SHORT).show()
            return false
        } else if (phone.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return if (password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Enter Password", Toast.LENGTH_SHORT).show()
                false
            } else {
                if (!phone.trim().matches(mobilePattern.toRegex())) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Enter a valid Mobile number",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    false
                } else
                    true
            }
        }
    }
}