package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        val signUpButton: Button = findViewById(R.id.signup1)
        signUpButton.setOnClickListener {
            // Retrieve user input manually using findViewById
            val usernameEditText = findViewById<EditText>(R.id.usernameEditText1)
            val emailEditText = findViewById<EditText>(R.id.emailEditText1)
            val passwordEditText = findViewById<EditText>(R.id.passwordEditText1)

            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Send user data to PHP file using Volley
            sendUserData(username, email, password)
        }
    }

    private fun sendUserData(username: String, email: String, password: String) {
        // Define the URL of your PHP file
        val url = "http://172.16.48.199/Tutorial/register_project.php"

        // Create a StringRequest with POST method
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                // Handle response from PHP file
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()

                // Navigate to LoginActivity after sign-up (optional)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                // Handle error
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {

            // Override getParams() to send data
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["email"] = email
                params["password"] = password
                return params
            }
        }

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest)
    }
}
