package com.example.labproject02

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DepositsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deposits)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val previousDepositsBtn = findViewById<ImageView>(R.id.btnPreviousDeposits)
        previousDepositsBtn.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }

        val addDepositBtn = findViewById<ImageView>(R.id.addDepositButton)
        addDepositBtn.setOnClickListener {
            val intent = Intent(this, addDepositActivity::class.java)
            startActivityForResult(intent, 100)
        }

        val curvedRectangleSaving1 = findViewById<View>(R.id.curvedRectangleSaving1)
        val curvedRectangleSaving2 = findViewById<View>(R.id.curvedRectangleSaving2)
        val curvedRectangleSaving3 = findViewById<View>(R.id.curvedRectangleSaving3) // Third deposit

        curvedRectangleSaving1.setOnClickListener {
            val intent = Intent(this, addDepositActivity::class.java)
            intent.putExtra("fixId", 1)
            startActivityForResult(intent, 100)
        }

        curvedRectangleSaving2.setOnClickListener {
            val intent = Intent(this, addDepositActivity::class.java)
            intent.putExtra("fixId", 2)
            startActivityForResult(intent, 100)
        }

        curvedRectangleSaving3.setOnClickListener {
            val intent = Intent(this, addDepositActivity::class.java)
            intent.putExtra("fixId", 3)
            startActivityForResult(intent, 100)
        }

        curvedRectangleSaving1.setOnLongClickListener {
            showResetConfirmationDialog(1)
            true
        }

        curvedRectangleSaving2.setOnLongClickListener {
            showResetConfirmationDialog(2)
            true
        }

        curvedRectangleSaving3.setOnLongClickListener {
            showResetConfirmationDialog(3)
            true
        }

        loadDepositData()
    }

    override fun onResume() {
        super.onResume()
        loadDepositData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            loadDepositData()
        }
    }

    private fun loadDepositData() {
        val sharedPreferences = getSharedPreferences("DepositDetails", MODE_PRIVATE)

        // Fix 1
        val fix1Name = sharedPreferences.getString("fix1_name", "FIX 1")
        val fix1Balance = sharedPreferences.getString("fix1_balance", "Rs. 0.00")
        val fix1Interest = sharedPreferences.getString("fix1_interest", "Rs. 0.00")
        val fix1Rate = sharedPreferences.getString("fix1_rate", "Rate: 0%")
        findViewById<TextView>(R.id.fixNameView1).text = fix1Name
        findViewById<TextView>(R.id.balanceView1).text = fix1Balance
        findViewById<TextView>(R.id.interestView1).text = fix1Interest
        findViewById<TextView>(R.id.textView2).text = fix1Rate

        // Fix 2
        val fix2Name = sharedPreferences.getString("fix2_name", "FIX 2")
        val fix2Balance = sharedPreferences.getString("fix2_balance", "Rs. 0.00")
        val fix2Interest = sharedPreferences.getString("fix2_interest", "Rs. 0.00")
        val fix2Rate = sharedPreferences.getString("fix2_rate", "Rate: 0%")
        findViewById<TextView>(R.id.fixNameView2).text = fix2Name
        findViewById<TextView>(R.id.balanceView2).text = fix2Balance
        findViewById<TextView>(R.id.interestView2).text = fix2Interest
        findViewById<TextView>(R.id.textView15).text = fix2Rate

        // Fix 3
        val fix3Name = sharedPreferences.getString("fix3_name", "FIX 3")
        val fix3Balance = sharedPreferences.getString("fix3_balance", "Rs. 0.00")
        val fix3Interest = sharedPreferences.getString("fix3_interest", "Rs. 0.00")
        val fix3Rate = sharedPreferences.getString("fix3_rate", "Rate: 0%")
        findViewById<TextView>(R.id.fixNameView3).text = fix3Name
        findViewById<TextView>(R.id.balanceView3).text = fix3Balance
        findViewById<TextView>(R.id.interestView3).text = fix3Interest
        findViewById<TextView>(R.id.rateView3).text = fix3Rate
    }

    private fun showResetConfirmationDialog(fixId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reset Deposit Details")
        builder.setMessage("Are you sure you want to reset the details of this deposit?")
        builder.setPositiveButton("Yes") { _, _ ->
            resetDepositData(fixId)
            Toast.makeText(this, "Deposit reset successfully.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }

    private fun resetDepositData(fixId: Int) {
        val sharedPreferences = getSharedPreferences("DepositDetails", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        when (fixId) {
            1 -> {
                editor.putString("fix1_name", "FIX 1")
                editor.putString("fix1_balance", "Rs. 0.00")
                editor.putString("fix1_interest", "Rs. 0.00")
                editor.putString("fix1_rate", "Rate: 0%")
            }
            2 -> {
                editor.putString("fix2_name", "FIX 2")
                editor.putString("fix2_balance", "Rs. 0.00")
                editor.putString("fix2_interest", "Rs. 0.00")
                editor.putString("fix2_rate", "Rate: 0%")
            }
            3 -> {
                editor.putString("fix3_name", "FIX 3")
                editor.putString("fix3_balance", "Rs. 0.00")
                editor.putString("fix3_interest", "Rs. 0.00")
                editor.putString("fix3_rate", "Rate: 0%")
            }
        }

        editor.apply()
        loadDepositData()
    }
}
