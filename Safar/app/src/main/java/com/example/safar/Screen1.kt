package com.example.safar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Screen1: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen1)

        val signInButton = findViewById<Button>(R.id.to_signin)
        // Set an OnClickListener on the button
        signInButton.setOnClickListener {
            // Create an Intent to start NextActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Optionally, call finish() if you don't want users to return to this screen
            // finish()
        }

    }

}

/*


package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class SelectSeatActivity : AppCompatActivity() {

    private lateinit var gridLayoutSeats: GridLayout
    private val seats = mutableListOf<Seat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_seats_screen)

        gridLayoutSeats = findViewById(R.id.gridLayoutSeats)

        // Initialize your seats data here
        initializeSeats()

        // Dynamically add seat views to the GridLayout
        addSeatsToGridLayout()

        // Setup the select seats button
        setupSelectSeatsButton()
    }

    private fun initializeSeats() {
        // Create a mock list of seats with different states
        for (row in 1..9) {
            for (column in 'A'..'D') {
                // Randomly assign seat states for demonstration
                val state = when ((1..10).random()) {
                    in 1..6 -> SeatState.AVAILABLE
                    in 7..8 -> SeatState.OCCUPIED
                    else -> SeatState.AVAILABLE
                }
                seats.add(Seat(row, column, state))
            }
        }
    }

    private fun addSeatsToGridLayout()
    {
        seats.forEach { seat ->
            val seatView = ImageView(this).apply {
                layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(seat.row, GridLayout.FILL, 1f),
                    GridLayout.spec(seat.column - 'A', GridLayout.FILL, 1f)
                ).apply {
                    width = 0
                    height = 0
                    setMargins(2, 2, 2, 2)
                }

                // Set the image resource based on the seat's state
                setImageResource(when (seat.state) {
                    SelectSeatActivity.SeatState.AVAILABLE -> R.drawable.ic_seatss
                    SelectSeatActivity.SeatState.OCCUPIED -> R.drawable.ic_seat_filled
                    else -> R.drawable.ic_seats_selected
                })

                // Set a click listener for the seat
                setOnClickListener {
                    // Handle the seat selection toggle here
                    when (seat.state) {
                        SelectSeatActivity.SeatState.AVAILABLE -> {
                            seat.state = SelectSeatActivity.SeatState.SELECTED
                            setImageResource(R.drawable.ic_seats_selected)
                        }
                        SelectSeatActivity.SeatState.SELECTED -> {
                            seat.state = SelectSeatActivity.SeatState.AVAILABLE
                            setImageResource(R.drawable.ic_seatss)
                        }
                        SelectSeatActivity.SeatState.OCCUPIED -> {
                            // Seat is already occupied
                        }
                    }
                }
            }

            // Add seat image view to the grid layout
            gridLayoutSeats.addView(seatView)
        }
    }

    private fun setupSelectSeatsButton() {
        val selectSeatsButton = findViewById<Button>(R.id.buttonSelectSeats)
        selectSeatsButton.setOnClickListener {
            // Get selected seats
            val selectedSeats = seats.filter { it.state == SeatState.SELECTED }

            // Assign random seat numbers to selected seats
            selectedSeats.forEach { seat ->
                seat.seatNumber = (1..36).random()
            }

            // Call function to send selected seat data to PHP script
            sendSelectedSeatsToPHP(selectedSeats.map { it.seatNumber.toString() })

            // Show a toast message
            showToast("Seats selected successfully")

            // Navigate to HomeActivity
            navigateToHomeActivity()
        }
    }

    private fun sendSelectedSeatsToPHP(selectedSeats: List<String>)
    {
        val url = "http://10.0.2.2/Tutorial/selectedseats.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                // Handle response from PHP script if needed
                // For example, display a toast message
                // Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                // Handle error if request fails
                // For example, display a toast message
                // Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["selected_seats"] = selectedSeats.joinToString(",") // Convert list to comma-separated string
                return params
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    data class Seat(val row: Int, val column: Char, var state: SeatState, var seatNumber: Int = 0)

    enum class SeatState {
        AVAILABLE, SELECTED, OCCUPIED
    }
}
*/
