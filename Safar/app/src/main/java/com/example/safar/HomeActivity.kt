package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homescreen)//forget password later

        // Fetch username from intent
        val username = intent.getStringExtra("username")

        // Find all buttons by their IDs
        val menu1Button = findViewById<Button>(R.id.menu1)
        val notify1Button = findViewById<Button>(R.id.notify1)
        val buyTicketButton = findViewById<ImageButton>(R.id.buyticketbtn)
        val bookTicketButton = findViewById<ImageButton>(R.id.bookticketbtn)
        val refundButton = findViewById<ImageButton>(R.id.refundbtn)
        val resetPasswordButton = findViewById<ImageButton>(R.id.resetpass)
        val feedbackButton = findViewById<ImageButton>(R.id.feedbackbtn)
        val popularRoutesButton = findViewById<ImageButton>(R.id.popular_routesbtn)

        // Set click listeners for each button
//        menu1Button.setOnClickListener {
//            // Define the intent for the action you want to perform
//            val intent = Intent(this, MenuActivity::class.java)
//            // Start the activity associated with the intent
//            startActivity(intent)
//        }
//
//        notify1Button.setOnClickListener {
//            // Define the intent for the action you want to perform
//            val intent = Intent(this, NotificationActivity::class.java)
//            // Start the activity associated with the intent
//            startActivity(intent)
//        }

        buyTicketButton.setOnClickListener {
            // Define the intent for the action you want to perform
            val intent = Intent(this, BuyPreBookedActivity::class.java)
            // Start the activity associated with the intent
            startActivity(intent)
        }

        bookTicketButton.setOnClickListener {
            // Define the intent for the action you want to perform
            val intent = Intent(this, ArrivalScreenActivity::class.java)
            // Start the activity associated with the intent
            startActivity(intent)
        }

        refundButton.setOnClickListener {
            // Define the intent for the action you want to perform
            val intent = Intent(this, RefundActivity::class.java)
            // Start the activity associated with the intent
            startActivity(intent)
        }

        resetPasswordButton.setOnClickListener {
            // Define the intent for the action you want to perform
            val intent = Intent(this, CustomerSupportActivity::class.java)
            // Start the activity associated with the intent
            startActivity(intent)
        }

        feedbackButton.setOnClickListener {
            // Define the intent for the action you want to perform
            val intent = Intent(this, Feedback::class.java)
            // Start the activity associated with the intent
            startActivity(intent)
        }

        popularRoutesButton.setOnClickListener {
            // Define the intent for the action you want to perform
            val intent = Intent(this, PopularRoutesActivity::class.java)
            // Start the activity associated with the intent
            startActivity(intent)
        }


        // Find the TextView for the username
        val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
        // Set the username to the TextView
        usernameTextView.text = username
    }
}
