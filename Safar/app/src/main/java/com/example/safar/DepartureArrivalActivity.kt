package com.example.safar
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class DepartureArrivalActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var departureLocationTextView: TextView
    private lateinit var arrivalLocationTextView: TextView
    private lateinit var dateAndDayTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bus_details)

        // Initialize views
        departureLocationTextView = findViewById(R.id.departureLocationTextView)
        arrivalLocationTextView = findViewById(R.id.arrivalLocationTextView)
        dateAndDayTextView = findViewById(R.id.dateAndDayTextView)

        // Fetch latest departure and arrival locations
        fetchLatestLocations()

        // Fetch latest date
        fetchLatestDate()

        // Sample data list (remove this if not needed)
        val dataList = listOf(
            SampleData("Bus 1", "5", "10:00 AM", "2 hours", "12:00 PM", "City A", "City B", "One Way", "$20"),
            SampleData("Bus 2", "10", "11:00 AM", "3 hours", "2:00 PM", "City C", "City D", "Round Trip", "$30"),
            SampleData("Bus 3", "8", "1:00 PM", "4 hours", "5:00 PM", "City E", "City F", "One Way", "$25")
            // Add more sample data if needed
        )

        // Initialize RecyclerView and set layout manager
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create and set adapter
        val adapter = CustomAdapter(dataList, this)
        recyclerView.adapter = adapter
    }

    // Method to fetch latest departure and arrival locations
    // Method to fetch latest departure and arrival locations
    private fun fetchLatestLocations() {
        val url = "http://172.16.48.199/Tutorial/get_latest_locations.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val locations = response.split(",")
                val departureCity = locations[0]
                val arrivalCity = locations[1]

                // Update the sample data with the fetched cities
                // Sample data list (remove this if not needed)
                val dataList = listOf(
                    SampleData("sfr-009", "5", "10:00 AM", "4 hours", "2:00 PM", departureCity, arrivalCity, "Economy", "Rs.2000"),
                    SampleData("sfr-010", "10", "11:00 AM", "4 hours", "3:00 PM", departureCity, arrivalCity, "Luxury", "Rs.3000"),
                    SampleData("sfr-011", "8", "1:00 PM", "5 hours", "6:00 PM", departureCity, arrivalCity, "Gold", "Rs3500")
                    // Add more sample data if needed
                )


                // Initialize RecyclerView and set layout manager
                val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this)

                // Create and set adapter
                val adapter = CustomAdapter(dataList, this)
                recyclerView.adapter = adapter

                // Update the TextViews with the fetched cities
                departureLocationTextView.text = departureCity
                arrivalLocationTextView.text = arrivalCity
            },
            Response.ErrorListener { error ->
                // Handle error
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }


    // Method to fetch latest date
    private fun fetchLatestDate() {
        val url = "http://172.16.48.199/Tutorial/get_latest_date.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                dateAndDayTextView.text = response
            },
            Response.ErrorListener { error ->
                // Handle error
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }

    override fun onItemClick(item: SampleData) {
        val url = "http://172.16.48.199/Tutorial/busdetails.php"

        // Start the SelectSeatActivity
        val intent = Intent(this@DepartureArrivalActivity, SelectSeatActivity::class.java)
        intent.putExtra("selectedItem", item)
        startActivity(intent)

        // Make network request
        val request = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                // Handle response if needed
                Toast.makeText(this@DepartureArrivalActivity, response, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                // Handle error
                Toast.makeText(this@DepartureArrivalActivity, "Error: " + error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["bus_plate_number"] = item.busName
                params["total_price"] = item.price
                return params
            }
        }
        Volley.newRequestQueue(this).add(request)
    }


}

private fun Intent.putExtra(s: String, item: SampleData) {


}
