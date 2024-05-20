package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ArrivalScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arrival_inp_screen)

        // Define arrival and departure city arrays
        val arrivalCities = arrayOf(
            "Islamabad", "Karachi", "Lahore", "Faisalabad", "Rawalpindi", "Multan",
            "Gujranwala", "Hyderabad", "Peshawar", "Quetta", "Sialkot", "Gujrat",
            "Bahawalpur", "Sargodha", "Sukkur", "Larkana", "Sheikhupura", "Mirpur Khas",
            "Rahim Yar Khan", "Jhang", "Abbottabad"
        )

        val departureCities = arrayOf(
            "Islamabad", "Karachi", "Lahore", "Faisalabad", "Rawalpindi", "Multan",
            "Gujranwala", "Hyderabad", "Peshawar", "Quetta", "Sialkot", "Gujrat",
            "Bahawalpur", "Sargodha", "Sukkur", "Larkana", "Sheikhupura", "Mirpur Khas",
            "Rahim Yar Khan", "Jhang", "Abbottabad"
        )


        // Create ArrayAdapter instances for arrival and departure cities
        val arrivalAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrivalCities)
        val departureAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, departureCities)

        // Find AutoCompleteTextViews by their IDs
        val arrivalAutoCompleteTextView =
            findViewById<AutoCompleteTextView>(R.id.arrivalAutoCompleteTextView)
        val departureAutoCompleteTextView =
            findViewById<AutoCompleteTextView>(R.id.departureAutoCompleteTextView)

        // Set adapters for AutoCompleteTextViews
        arrivalAutoCompleteTextView.setAdapter(arrivalAdapter)
        departureAutoCompleteTextView.setAdapter(departureAdapter)

        // Find the Confirm button by its ID
        val confirmBtn = findViewById<Button>(R.id.Confirm_arrival_btn)

        // Set OnClickListener for the Confirm button
        confirmBtn.setOnClickListener {
            // Retrieve selected arrival and departure cities
            val arrivalCity = arrivalAutoCompleteTextView.text.toString()
            val departureCity = departureAutoCompleteTextView.text.toString()

            // Check if both arrival and departure cities are selected
            if (arrivalCity.isNotEmpty() && departureCity.isNotEmpty()) {
                // Call function to send data to PHP script
                sendDataToPHP(arrivalCity, departureCity)
            } else {
                // Display an error message indicating both cities must be selected
                Toast.makeText(this, "Please select both arrival and departure cities", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendDataToPHP(arrivalCity: String, departureCity: String) {
        // Define the URL of your PHP script
        val url = "http://172.16.48.199/Tutorial/traveldetails.php"

        // Create a StringRequest with POST method
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                // Log the response from PHP script
                Log.d("SendDataToPHP", "Response: $response")

                // Start the CalendarActivity after storing data
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                // Handle error if request fails
                // For example, display a toast message
                // Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("SendDataToPHP", "Error: ${error.message}")
            }) {

            // Override getParams() to send data
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["arrival_city"] = arrivalCity
                params["departure_city"] = departureCity

                // Log the parameters being sent
                Log.d("SendDataToPHP", "Params: $params")
                return params
            }
        }

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest)
    }
}
