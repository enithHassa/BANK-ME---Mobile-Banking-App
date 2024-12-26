package com.example.labproject02

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HomepageActivity : AppCompatActivity() {

    private lateinit var dateView: TextView
    private lateinit var timeView: TextView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        dateView = findViewById(R.id.dateView)
        timeView = findViewById(R.id.timeView)

        // Start updating date and time
        updateDateTime()

        val savingsBtn = findViewById<ImageView>(R.id.btnSavings)
        savingsBtn.setOnClickListener {
            val intent = Intent(this, SavingsActivity::class.java)
            startActivity(intent)
        }

        val depositsBtn = findViewById<ImageView>(R.id.btnDeposits)
        depositsBtn.setOnClickListener {
            val intent = Intent(this, DepositsActivity::class.java)
            startActivity(intent)
        }

        val notifiBtn = findViewById<ImageView>(R.id.btnViewNotification)
        notifiBtn.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun updateDateTime() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()) // Format for date
        val timeFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()) // Format for time

        val runnable = object : Runnable {
            override fun run() {
                val currentTime = Calendar.getInstance().time
                dateView.text = dateFormat.format(currentTime)
                timeView.text = timeFormat.format(currentTime)

                // Update every second for time changes
                handler.postDelayed(this, 1000)
            }
        }

        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Stop handler when activity is destroyed
    }
}
