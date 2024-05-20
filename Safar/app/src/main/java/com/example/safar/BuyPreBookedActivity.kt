package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class BuyPreBookedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_prebookedticket)

        val searchButton = findViewById<Button>(R.id.search_btn)
        val departureEditText = findViewById<EditText>(R.id.departure_edit_text)
        val arrivalEditText = findViewById<EditText>(R.id.arrival_edit_text)

        searchButton.setOnClickListener {
            val departureCity = departureEditText.text.toString()
            val arrivalCity = arrivalEditText.text.toString()

            // Make HTTP POST request to the PHP script
            GlobalScope.launch(Dispatchers.IO) {
                val result = fetchDataFromServer(departureCity, arrivalCity)

                runOnUiThread {
                    // Handle the result here
                    if (result.isNotEmpty()) {
                        // Start PreBookedActivity and pass the fetched data
                        val intent = Intent(this@BuyPreBookedActivity, PreBookedActivity::class.java)
                        intent.putExtra("traveldetails", result)
                        startActivity(intent)
                    } else {
                        // Show error message or handle no data scenario
                    }
                }
            }
        }
    }

    private fun fetchDataFromServer(departureCity: String, arrivalCity: String): String {
        val url = "http://172.16.48.199/Tutorial/displayticket.php"
        val connection = URL(url).openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "POST"
            connection.doOutput = true

            val postData = "departureCity=$departureCity&arrivalCity=$arrivalCity"
            val writer = OutputStreamWriter(connection.outputStream)
            writer.write(postData)
            writer.flush()

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()

            var line: String? = reader.readLine()
            while (line != null) {
                response.append(line)
                line = reader.readLine()
            }

            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "" // Return empty string on error
        } finally {
            connection.disconnect()
        }
    }
}
