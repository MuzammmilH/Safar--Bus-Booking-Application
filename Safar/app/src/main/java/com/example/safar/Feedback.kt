package com.example.safar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Feedback : AppCompatActivity() {

    private lateinit var feedbackEditText: EditText
    private lateinit var submitFeedbackButton: Button
    private lateinit var feedbackTextView: TextView
    private lateinit var databaseHandler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback)

        feedbackEditText = findViewById(R.id.feedbacktext)
        submitFeedbackButton = findViewById(R.id.submit_feedback_btn)
        feedbackTextView = findViewById(R.id.feedback_text_view)
        databaseHandler = DatabaseHelper(this)

       /* val storedFeedback = databaseHandler.getAllFeedback()
        if (storedFeedback.isNotEmpty()) {
            feedbackTextView.text = storedFeedback.joinToString("\n")
        } else {
            feedbackTextView.text = "No feedback submitted yet."
        }*/

        submitFeedbackButton.setOnClickListener {
            val feedbackMessage = feedbackEditText.text.toString()
            if (feedbackMessage.isNotEmpty()) {
                val feedbackModel = FeedbackModel(0, feedbackMessage)
                databaseHandler.addFeedback(feedbackModel)
                feedbackTextView.append("\n$feedbackMessage")
                Toast.makeText(this, "Feedback submitted successfully.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter your feedback.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
