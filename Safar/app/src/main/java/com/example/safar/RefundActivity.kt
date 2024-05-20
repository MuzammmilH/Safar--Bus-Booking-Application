package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RefundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.refund)


        val refundButton = findViewById<Button>(R.id.reset_forgot_btn)

        refundButton.setOnClickListener {
            // Define the intent for the action you want to perform
            val intent = Intent(this, HomeActivity::class.java)
            // Start the activity associated with the intent
            startActivity(intent)
        }

    }

}