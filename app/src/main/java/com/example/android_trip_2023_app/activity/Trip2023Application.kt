package com.example.android_trip_2023_app.activity

import com.example.android_trip_2023_app.utils.DatabaseHelper
import android.app.Application

class Trip2023Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // データベースの初期化
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase
        db.close()
    }
}