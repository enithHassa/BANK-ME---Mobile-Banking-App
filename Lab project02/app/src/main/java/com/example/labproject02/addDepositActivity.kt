package com.example.labproject02

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.concurrent.TimeUnit

class addDepositActivity : AppCompatActivity() {

    private fun sendNotification(fixName: String, endTimeInMillis: Long, isDeleted: Boolean = false) {
        val notificationId = System.currentTimeMillis().toInt() // Unique ID for each notification
        val channelId = "deposit_notifications"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Deposit Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, NotificationActivity::class.java).apply {
            putExtra("fixName", fixName)
            putExtra("endTime", endTimeInMillis) // Pass end time for tracking
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val contentText = if (isDeleted) {
            "$fixName deposit has been deleted."
        } else {
            "New deposit data added successfully! Track it here..."
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.bellicon)
            .setContentTitle(if (isDeleted) "Deposit Deleted" else "New Deposit Added")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(android.net.Uri.parse("android.resource://${packageName}/${R.raw.audio}"))
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private var fixId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_deposit)

        fixId = intent.getIntExtra("fixId", 0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val goBackBtn = findViewById<ImageView>(R.id.btnPreviousDeposits)
        goBackBtn.setOnClickListener {
            val intent = Intent(this, DepositsActivity::class.java)
            startActivity(intent)
        }

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {
            val fixName = findViewById<EditText>(R.id.fixNameView).text.toString()
            val depositAmount = findViewById<EditText>(R.id.depositAmountView).text.toString()
            val depositRate = findViewById<EditText>(R.id.depositRateView).text.toString()
            val durationDays = 180// Assume fixed duration for now
            val endTimeInMillis = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(durationDays.toLong())
            val interest = calculateInterest(depositAmount, depositRate)

            val sharedPreferences = getSharedPreferences("DepositDetails", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Store deposit details based on fixId
            when (fixId) {
                1 -> {
                    editor.putString("fix1_name", fixName)
                    editor.putString("fix1_balance", "Rs. $depositAmount")
                    editor.putString("fix1_interest", "Rs. $interest")
                    editor.putString("fix1_rate", "Rate: $depositRate%")
                    editor.putLong("${fixName}_end_time", endTimeInMillis)
                }
                2 -> {
                    editor.putString("fix2_name", fixName)
                    editor.putString("fix2_balance", "Rs. $depositAmount")
                    editor.putString("fix2_interest", "Rs. $interest")
                    editor.putString("fix2_rate", "Rate: $depositRate%")
                    editor.putLong("${fixName}_end_time", endTimeInMillis)
                }
                3 -> {
                    editor.putString("fix3_name", fixName)
                    editor.putString("fix3_balance", "Rs. $depositAmount")
                    editor.putString("fix3_interest", "Rs. $interest")
                    editor.putString("fix3_rate", "Rate: $depositRate%")
                    editor.putLong("${fixName}_end_time", endTimeInMillis)
                }
            }

            val existingNotifications = sharedPreferences.getStringSet("notifications", mutableSetOf()) ?: mutableSetOf()
            existingNotifications.add("New deposit added for $fixName")

            editor.putStringSet("notifications", existingNotifications)
            editor.apply()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    sendNotification(fixName, endTimeInMillis)
                }
            } else {
                sendNotification(fixName, endTimeInMillis)
            }

            setResult(RESULT_OK)
            finish()
        }

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            finish()
        }
    }

    private fun calculateInterest(amount: String, rate: String): String {
        val amt = amount.toDoubleOrNull() ?: 0.0
        val rt = rate.toDoubleOrNull() ?: 0.0
        val interest = amt * rt / 100
        return String.format("%.2f", interest)
    }
}
