package com.example.safar

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PreBookedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prebooked_ticket)

        // Receive data passed from the previous activity
        val travelDetails = intent.getStringExtra("traveldetails")

        // Parse JSON response and extract data
//        var departureCity = ""
//        var arrivalCity = ""
//        var departureTime = ""
//        var travelTime = ""
//        var arrivalTime = ""
//        var departurePlace = ""
//        var arrivalPlace = ""
//        var oneWayInfo = ""
//        var price = ""
//        try {
//            val jsonObject = JSONObject(travelDetails)
//            departureCity = jsonObject.getString("departureCity")
//            arrivalCity = jsonObject.getString("arrivalCity")
//            departureTime = jsonObject.getString("departureTime")
//            travelTime = jsonObject.getString("travelTime")
//            arrivalTime = jsonObject.getString("arrivalTime")
//            departurePlace = jsonObject.getString("departurePlace")
//            arrivalPlace = jsonObject.getString("arrivalPlace")
//            oneWayInfo = jsonObject.getString("oneWayInfo")
//            price = jsonObject.getString("price")
//        } catch (e: Exception) {
//            e.printStackTrace()
//            // Handle exceptions or errors
//        }

        val departureCity = "Lahore"
        val arrivalCity = "Multan"
        val departureTime = "10:00 AM" // Updated departure time
        val travelTime = "2 hours" // Assuming 2 hours travel time
        val arrivalTime = "12:00 PM" // Updated arrival time
        val oneWayInfo = "One way" // Hardcoded one way info
        val price = "Rs. 2200" // Hardcoded price


        // Find TextViews in the layout
        val departurePlaceTextView = findViewById<TextView>(R.id.departurePlaceTextView)
        val arrivalPlaceTextView = findViewById<TextView>(R.id.arrivalPlaceTextView)
        val departureTimeTextView = findViewById<TextView>(R.id.departureTimeTextView)
        val travelTimeTextView = findViewById<TextView>(R.id.travelTimeTextView)
        val arrivalTimeTextView = findViewById<TextView>(R.id.arrivalTimeTextView)
        val oneWayTextView = findViewById<TextView>(R.id.oneWayTextView)
        val priceTextView = findViewById<TextView>(R.id.priceTextView)

        // Set text to TextViews
        departurePlaceTextView.text = departureCity
        arrivalPlaceTextView.text = arrivalCity
        departureTimeTextView.text = departureTime
        travelTimeTextView.text = travelTime
        arrivalTimeTextView.text = arrivalTime
        oneWayTextView.text = oneWayInfo
        priceTextView.text = price
    }
}
