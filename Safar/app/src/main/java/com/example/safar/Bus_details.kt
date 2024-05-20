package com.example.safar


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Bus_details: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bus_details)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up your sample data (replace with your actual data)
        val sampleDataList = getSampleData()

        // Initialize and set the adapter
//val adapter = CustomAdapter(sampleDataList)
        //recyclerView.adapter = adapter
    }

    // Method to provide sample data (replace with your actual data source)
    private fun getSampleData(): List<SampleData> {
        return listOf(
            SampleData(
                "sfr-009",
                "4 Seats Left",
                "Dept:10:00 AM",
                "5 hrs",
                "Arrival:12:00 PM",
                "Departure Place",
                "Arrival Place",
                "Economy",
                "Rs.2000"
            ),
            SampleData(
                "sfr-010",
                "8 Seats Left",
                "Dept:12:00 PM",
                "5 hrs",
                "Arrival:2:30 PM",
                "Departure Place",
                "Arrival Place",
                "Luxury",
                "Rs.3000"
            ),
            SampleData(
                "sfr-011",
                "2 Seats Left",
                "Dept:2:00 PM",
                "5 hrs",
                "Arrival:7:00 PM",
                "Departure Place",
                "Arrival Place",
                "Gold",
                "Rs.3500"
            )
        )
    }
}