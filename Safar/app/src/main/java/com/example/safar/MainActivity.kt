package com.example.safar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen1_popup)

// Handler to post a delayed task
        Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent to start LoginActivity
            val intent = Intent(this, Screen1::class.java)
            startActivity(intent)
            // Optionally, call finish() if you don't want users to return to this screen
            finish()
        }, 2000) // Delay of 5 seconds (5000 milliseconds)
    }
}