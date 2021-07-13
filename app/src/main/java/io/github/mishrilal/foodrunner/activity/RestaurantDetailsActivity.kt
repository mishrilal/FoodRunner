package io.github.mishrilal.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import io.github.mishrilal.foodrunner.R

class RestaurantDetailsActivity : AppCompatActivity() {
    lateinit var txtResDetailID: TextView
    private lateinit var toolbar: Toolbar

    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

        title = intent.getStringExtra("title").toString()

        init()
        setupToolbar()
        txtResDetailID.text = intent.getIntExtra("id", 0).toString()

        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    private fun init() {
        txtResDetailID = findViewById(R.id.txtResDetailID)
        toolbar = findViewById(R.id.toolbar)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
    }


}