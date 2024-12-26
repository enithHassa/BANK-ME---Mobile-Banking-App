package com.example.labproject02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginBackBtn = findViewById<Button>(R.id.btnLoginBack)
        loginBackBtn.setOnClickListener {
            val intent = Intent (this, SignupActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.btnLoginpage)
        loginBtn.setOnClickListener {
            val intent = Intent (this, HomepageActivity::class.java)
            startActivity(intent)
        }
    }
}