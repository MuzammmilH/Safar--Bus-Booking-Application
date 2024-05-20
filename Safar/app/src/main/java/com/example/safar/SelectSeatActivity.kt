package com.example.safar
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class SelectSeatActivity : AppCompatActivity() {

    private lateinit var gridLayoutSeats: GridLayout
    private val seats = mutableListOf<Seat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_seats_screen)

        // Initialize Firebase, if not already initialized
        // FirebaseApp.initializeApp(this) // Uncomment this line if Firebase is not initialized

        // Subscribe to the "/topics/all" topic
        FirebaseMessaging.getInstance().subscribeToTopic("all")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Successfully subscribed to topic 'all'")
                } else {
                    Log.e(TAG, "Failed to subscribe to topic 'all'", task.exception)
                }
            }

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

    private fun addSeatsToGridLayout() {
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
                    SeatState.AVAILABLE -> R.drawable.ic_seatss
                    SeatState.OCCUPIED -> R.drawable.ic_seat_filled
                    else -> R.drawable.ic_seats_selected
                })

                // Set a click listener for the seat
                setOnClickListener {
                    // Handle the seat selection toggle here
                    when (seat.state) {
                        SeatState.AVAILABLE -> {
                            seat.state = SeatState.SELECTED
                            setImageResource(R.drawable.ic_seats_selected)
                        }
                        SeatState.SELECTED -> {
                            seat.state = SeatState.AVAILABLE
                            setImageResource(R.drawable.ic_seatss)
                        }
                        SeatState.OCCUPIED -> {
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

            // Send push notification
            sendPushNotification("Seats booked Successfully")

            // Navigate to HomeActivity
            navigateToHomeActivity()
        }
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

    private fun sendSelectedSeatsToPHP(selectedSeats: List<String>) {
        // Implement sending selected seat data to PHP script if needed
        // This function is left empty for demonstration
    }

    private fun sendPushNotification(message: String) {
        val json = JSONObject().apply {
            put("to", "/topics/all") // Send notification to all devices subscribed to topic 'all'
            put("notification", JSONObject().apply {
                put("title", "Seat Booking")
                put("body", message)
                put("icon", "ic_notification") // Set a valid small icon for the notification
            })
        }

        val client = OkHttpClient()
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        val request = Request.Builder()
            .url("https://fcm.googleapis.com/fcm/send")
            .post(requestBody)
            .addHeader("Authorization", "key=") // Replace YOUR_SERVER_KEY with your server key from Firebase Console
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                println(response.body?.string())
            }
        })
    }


    data class Seat(val row: Int, val column: Char, var state: SeatState, var seatNumber: Int = 0)

    enum class SeatState {
        AVAILABLE, SELECTED, OCCUPIED
    }

    companion object {
        const val TAG = "SelectSeatActivity"
    }
}
