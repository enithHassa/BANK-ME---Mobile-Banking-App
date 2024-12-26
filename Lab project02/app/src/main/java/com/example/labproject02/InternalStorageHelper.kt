package com.example.labproject02

import android.content.Context

class InternalStorageHelper {
    companion object {
        fun writeData(context: Context, key: String, value: String) {
            val sharedPreferences = context.getSharedPreferences("DepositDetails", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun readData(context: Context, key: String, defaultValue: String = ""): String {
            val sharedPreferences = context.getSharedPreferences("DepositDetails", Context.MODE_PRIVATE)
            return sharedPreferences.getString(key, defaultValue) ?: defaultValue
        }
    }
}
