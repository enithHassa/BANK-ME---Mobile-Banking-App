package com.example.labproject02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OnboardActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboard1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val onboardBtn1 = findViewById<Button>(R.id.onboardNextbtn1)
        onboardBtn1.setOnClickListener {
            val intent = Intent (this, OnboardActivity2::class.java)
            startActivity(intent)
        }

        val skipbtn = findViewById<Button>(R.id.skipBtn)
        skipbtn.setOnClickListener {
            val intent = Intent (this, HomepageActivity::class.java)
            startActivity(intent)
        }

    }
}