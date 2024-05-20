// RouteModel.kt
package com.example.safar

data class RouteModel(
    var id: Int = -1,
    var startCity: String = "",
    var destinationCity: String = "",
    var date: String = "",
    var address: String = "",
    var price: String = ""
)
