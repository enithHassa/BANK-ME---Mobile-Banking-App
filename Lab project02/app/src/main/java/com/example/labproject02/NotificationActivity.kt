package com.example.labproject02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val notificationContainer = findViewById<LinearLayout>(R.id.notificationContainer)
        val sharedPreferences = getSharedPreferences("DepositDetails", MODE_PRIVATE)
        val notifications = sharedPreferences.getStringSet("notifications", setOf())?.toList() ?: listOf()

        for (notification in notifications) {
            val notificationView = layoutInflater.inflate(R.layout.item_notification, notificationContainer, false)

            val notificationTextView = notificationView.findViewById<TextView>(R.id.notificationTextView)
            notificationTextView.text = notification

            // Extract fix name and end time
            val fixName = notification.substringAfter("New deposit added for ").trim()
            val endTime = sharedPreferences.getLong("${fixName}_end_time", 0L)


            // See-More button functionality
            val seeMoreBtn = notificationView.findViewById<Button>(R.id.seeMoreBtn)
            seeMoreBtn.setOnClickListener {
                val intent = Intent(this, TrackDateActivity::class.java).apply {
                    val fixName = notification.substringAfter("New deposit added for ").trim()
                    val endTime = sharedPreferences.getLong("${fixName}_end_time", 0L)
                    putExtra("fixName", fixName)
                    putExtra("endTime", endTime)
                }
                startActivity(intent)
            }


            // Delete button functionality
            val deleteBtn = notificationView.findViewById<Button>(R.id.deleteBtn)
            deleteBtn.setOnClickListener {
                notificationContainer.removeView(notificationView)
                val updatedNotifications = sharedPreferences.getStringSet("notifications", mutableSetOf())?.toMutableSet()
                updatedNotifications?.remove(notification)
                sharedPreferences.edit().putStringSet("notifications", updatedNotifications).apply()
            }

            notificationContainer.addView(notificationView)
        }

        // Set the button to go back to HomepageActivity
        val previousNotifiBtn = findViewById<ImageView>(R.id.previousBtnNotifi)
        previousNotifiBtn.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }
    }
}
