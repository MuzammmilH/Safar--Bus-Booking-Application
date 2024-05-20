package com.example.safar

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class PopularRoutesActivity : AppCompatActivity() {

    private lateinit var routes: List<RouteModel>
    private lateinit var routesContainer: LinearLayout
    private lateinit var editTextSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popular_routes)

        routes = getPopularRoutes()

        routesContainer = findViewById(R.id.routesContainer)
        editTextSearch = findViewById(R.id.editTextSearch)

        displayRoutes(routes)

        editTextSearch.setOnEditorActionListener { _, _, _ ->
            filterRoutes(editTextSearch.text.toString())
            true
        }
    }

    private fun displayRoutes(routes: List<RouteModel>) {
        routesContainer.removeAllViews()
        for (route in routes) {
            val routeView = layoutInflater.inflate(R.layout.route_item, null)

            // Find views within the inflated route_item layout
            val imageViewBus = routeView.findViewById<ImageView>(R.id.imageViewBus)
            val textViewRoute = routeView.findViewById<TextView>(R.id.textViewRoute)
            val textViewDate = routeView.findViewById<TextView>(R.id.textViewDate)
            val textViewAddress = routeView.findViewById<TextView>(R.id.textViewAddress)
            val textViewPrice = routeView.findViewById<TextView>(R.id.textViewPrice)

            // Set data for each view
            textViewRoute.text = "${route.startCity} to ${route.destinationCity}"
            textViewDate.text = route.date
            textViewAddress.text = route.address
            textViewPrice.text = "Rs.${route.price}"

            // Generate a random number to choose between the two images
            val random = Random.nextInt(2) // Generates either 0 or 1

            // Set the image based on the random number
            if (random == 0) {
                imageViewBus.setImageResource(R.mipmap.bus1)
            } else {
                imageViewBus.setImageResource(R.mipmap.bus2)
            }

            // Add the inflated route view to the routesContainer
            routesContainer.addView(routeView)
        }
    }

    private fun filterRoutes(query: String) {
        val filteredRoutes = routes.filter { route ->
            route.startCity.contains(query, ignoreCase = true) || route.destinationCity.contains(query, ignoreCase = true)
        }
        displayRoutes(filteredRoutes)
    }

    // Dummy function to get popular routes, replace this with your actual logic
    private fun getPopularRoutes(): List<RouteModel> {
        return listOf(
            RouteModel(startCity = "Lahore", destinationCity = "Islamabad/Rawalpindi", date = "10-06-2024", address = "123 Main St", price = "2700"),
            RouteModel(startCity = "Karachi", destinationCity = "Lahore", date = "2024-06-02", address = "45 Bay Street New York, USA", price = "6000"),
            RouteModel(startCity = "Islamabad/Rawalpindi", destinationCity = "Gilgit", date = "11-04-2024", address = "45 Bay Street New York, USA", price = "2000"),
            RouteModel(startCity = "Lahore", destinationCity = "Multan", date = "20-01-2024", address = "45 Bay Street New York, USA", price = "3000"),
            RouteModel(startCity = "Islamabad/Rawalpindi", destinationCity = "Peshawar", date = "15-02-2024", address = "45 Bay Street New York, USA", price = "4000"),
            RouteModel(startCity = "Karachi", destinationCity = "Quetta", date = "12-12-2024", address = "45 Bay Street New York, USA", price = "1500"),
            RouteModel(startCity = "Chittagong", destinationCity = "Sylhet", date = "01-10-2024", address = "45 Bay Street New York, USA", price = "2500"),
            // Add more routes here
        )
    }
}
