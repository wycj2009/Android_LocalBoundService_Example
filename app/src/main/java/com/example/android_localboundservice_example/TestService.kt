package com.example.android_localboundservice_example

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TestService : Service() {

    private val testBinder: IBinder = TestBinder()

    override fun onBind(intent: Intent): IBinder {
        return testBinder
    }

    suspend fun getCurrentTime(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/ HH:mm:ss", Locale.getDefault()))
    }

    inner class TestBinder : Binder() {
        fun getService(): TestService = this@TestService
    }

}