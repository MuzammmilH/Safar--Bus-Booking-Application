package com.example.safar

enum class SeatState {
    AVAILABLE, SELECTED, OCCUPIED
}

data class Seat(val row: Int, val column: Char, var state: SeatState) {
    val columnIndex: Int
        get() = column.toUpperCase() - 'A' // 'A' will be 0, 'B' will be 1, etc.
}
