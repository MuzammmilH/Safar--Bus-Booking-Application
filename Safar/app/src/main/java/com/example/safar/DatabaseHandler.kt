
package com.example.safar

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "safar_db1"

        // Table name and column names
        private const val TABLE_ROUTES = "Routes"
        private const val ID = "id"
        private const val START_CITY = "startCity"
        private const val DESTINATION_CITY = "destinationCity"
        private const val DATE = "date"
        private const val ADDRESS = "address"
        private const val PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create routes table
        val CREATE_ROUTES_TABLE = "CREATE TABLE $TABLE_ROUTES (" +
                "$ID INTEGER PRIMARY KEY," +
                "$START_CITY TEXT," +
                "$DESTINATION_CITY TEXT," +
                "$DATE TEXT," +
                "$ADDRESS TEXT," +
                "$PRICE TEXT)"
        db.execSQL(CREATE_ROUTES_TABLE)

        // Add popular routes manually on creation
        Log.d("DatabaseHandler", "Adding popular routes manually...")
        addPopularRoutesManually(db)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed, and recreate
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ROUTES")
        onCreate(db)
    }

    fun addRoute(routeModel: RouteModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(START_CITY, routeModel.startCity)
            put(DESTINATION_CITY, routeModel.destinationCity)
            put(DATE, routeModel.date)
            put(ADDRESS, routeModel.address)
            put(PRICE, routeModel.price)
        }
        val result = db.insert(TABLE_ROUTES, null, contentValues)
        /*if (result == -1L) {
            Log.e("DatabaseHandler", "Failed to insert route into database")
        } else {
            Log.d("DatabaseHandler", "Route inserted successfully with ID: $result")
        }*/
        db.close()
    }

    // Method to add popular Pakistan routes manually
    private fun addPopularRoutesManually(db: SQLiteDatabase) {
        // Add popular Pakistan routes manually
        val routes = listOf(
            RouteModel(startCity = "Lahore", destinationCity = "Islamabad/Rawalpindi", date = "2024-06-01", address = "123 Main St", price = "50"),
            RouteModel(startCity = "Karachi", destinationCity = "Lahore", date = "2024-06-02", address = "456 Elm St", price = "70"),
            RouteModel(startCity = "Islamabad/Rawalpindi", destinationCity = "Gilgit", date = "2024-06-03", address = "789 Oak St", price = "100"),
            RouteModel(startCity = "Lahore", destinationCity = "Multan", date = "2024-06-04", address = "456 Baker St", price = "80"),
            RouteModel(startCity = "Islamabad/Rawalpindi", destinationCity = "Peshawar", date = "2024-06-05", address = "789 Oxford St", price = "120"),
            RouteModel(startCity = "Karachi", destinationCity = "Quetta", date = "2024-06-06", address = "123 Regent St", price = "90")
            // Add more routes here if needed
        )

        // Loop through the list and add each route to the database
        for (route in routes) {
            addRoute(route)
        }
    }

    // Method to retrieve all routes from the database
    @SuppressLint("Range")
    fun getAllRoutes(): List<RouteModel> {
        val routeList = mutableListOf<RouteModel>()
        val selectQuery = "SELECT * FROM $TABLE_ROUTES"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val routeModel = RouteModel().apply {
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    startCity = cursor.getString(cursor.getColumnIndex(START_CITY))
                    destinationCity = cursor.getString(cursor.getColumnIndex(DESTINATION_CITY))
                    date = cursor.getString(cursor.getColumnIndex(DATE))
                    address = cursor.getString(cursor.getColumnIndex(ADDRESS))
                    price = cursor.getString(cursor.getColumnIndex(PRICE))
                }
                routeList.add(routeModel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return routeList
    }
}
