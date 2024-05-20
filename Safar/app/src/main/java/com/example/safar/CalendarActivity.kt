package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class CalendarActivity : AppCompatActivity() {

    private var selectedDepartureDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calender_file)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val confirmBtn = findViewById<Button>(R.id.confirmButton)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Store the selected departure date in yyyy-MM-dd format
            selectedDepartureDate = "$year-${month + 1}-$dayOfMonth"
        }

        confirmBtn.setOnClickListener {
            // Check if a departure date is selected
            if (selectedDepartureDate.isNotEmpty()) {
                // Call function to send data to PHP script
                sendDataToPHP(selectedDepartureDate)
            } else {
                // Handle case where no departure date is selected
            }
        }
    }

    private fun sendDataToPHP(departureDate: String) {
        // Define the URL of your PHP script
        val url = "http://172.16.48.199/Tutorial/bustiming.php"

        // Create a StringRequest with POST method
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                // Handle response from PHP script if needed
                val intent = Intent(this, DepartureArrivalActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                // Handle error if request fails
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["departure_date"] = departureDate
                return params
            }
        }
        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest)
    }
}
