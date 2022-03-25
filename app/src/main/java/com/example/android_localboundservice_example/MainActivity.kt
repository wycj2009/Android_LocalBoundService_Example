package com.example.android_localboundservice_example

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.android_localboundservice_example.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var testService: TestService? = null

    private val testConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TestService.TestBinder
            testService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindService(
                Intent(this, TestService::class.java),
                testConnection,
                BIND_AUTO_CREATE
        )

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val curTime = testService?.getCurrentTime()

                withContext(Dispatchers.Main) {
                    binding.textView.text = curTime
                }
            }
        }
    }

}