package com.example.safar

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "safar_db"

        private const val TABLE_FEEDBACK = "Feedback"
        private const val ID = "id"
        private const val MESSAGE = "message"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_FEEDBACK_TABLE = "CREATE TABLE $TABLE_FEEDBACK (" +
                "$ID INTEGER PRIMARY KEY," +
                "$MESSAGE TEXT)"
        db.execSQL(CREATE_FEEDBACK_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FEEDBACK")
        onCreate(db)
    }

    fun addFeedback(feedbackModel: FeedbackModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(MESSAGE, feedbackModel.message)
        }
        db.insert(TABLE_FEEDBACK, null, contentValues)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllFeedback(): List<FeedbackModel> {
        val feedbackList = mutableListOf<FeedbackModel>()
        val selectQuery = "SELECT * FROM $TABLE_FEEDBACK"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val feedbackModel = FeedbackModel().apply {
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    message = cursor.getString(cursor.getColumnIndex(MESSAGE))
                }
                feedbackList.add(feedbackModel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return feedbackList
    }

}
