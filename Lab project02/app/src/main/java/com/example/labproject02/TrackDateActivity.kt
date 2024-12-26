package com.example.labproject02

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class TrackDateActivity : AppCompatActivity() {

    private lateinit var fixName: String
    private var remainingTimeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_date)

        fixName = intent.getStringExtra("fixName") ?: "Unknown Fix"
        val endTimeInMillis = intent.getLongExtra("endTime", 0L)

        val sharedPreferences = getSharedPreferences("DepositDetails", MODE_PRIVATE)
        if (endTimeInMillis == 0L) {
            // Retrieve endTime from SharedPreferences if not passed in the Intent
            val fixName = intent.getStringExtra("fixName") ?: "Unknown Fix"
            remainingTimeInMillis = sharedPreferences.getLong("${fixName}_end_time", 0L)
        } else {
            remainingTimeInMillis = endTimeInMillis - System.currentTimeMillis()
        }

        // Find views
        val fixNameView = findViewById<TextView>(R.id.viewTrackFixName)
        val daysView = findViewById<TextView>(R.id.daysView)
        val hoursView = findViewById<TextView>(R.id.hoursView)
        val minutesView = findViewById<TextView>(R.id.minutesView)
        val secondsView = findViewById<TextView>(R.id.secondsView)

        // Set the fix name
        fixNameView.text = fixName

        // Calculate remaining time
        remainingTimeInMillis = endTimeInMillis - System.currentTimeMillis()

        // Start countdown timer
        if (remainingTimeInMillis > 0) {
            object : CountDownTimer(remainingTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                    val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                    val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                    // Update the UI
                    daysView.text = "$days Days"
                    hoursView.text = "$hours Hours"
                    minutesView.text = "$minutes Minutes"
                    secondsView.text = "$seconds Seconds"
                }

                override fun onFinish() {
                    daysView.text = "0 Days"
                    hoursView.text = "0 Hours"
                    minutesView.text = "0 Minutes"
                    secondsView.text = "0 Seconds"
                }
            }.start()
        } else {
            // If the time has already passed, set everything to 0
            daysView.text = "0 Days"
            hoursView.text = "0 Hours"
            minutesView.text = "0 Minutes"
            secondsView.text = "0 Seconds"
        }

        // Handle the back button
        val previousTrackBtn = findViewById<ImageView>(R.id.previousTrackBtn)
        previousTrackBtn.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }
}
