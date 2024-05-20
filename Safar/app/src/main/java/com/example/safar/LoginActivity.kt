package com.example.safar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LoginActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.sigin)

                val signInButton: Button = findViewById(R.id.signinButton)
                signInButton.setOnClickListener {
                        val emailEditText: EditText = findViewById(R.id.emailEditText1)
                        val passwordEditText: EditText = findViewById(R.id.passwordEditText1)
                        val email = emailEditText.text.toString().trim()
                        val password = passwordEditText.text.toString().trim()

                        if (email.isEmpty() || password.isEmpty()) {
                                Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show()
                        } else {
                                login(email, password)
                        }
                }

                val forgotPasswordTextView: TextView = findViewById(R.id.forgotPasswordTextView)
                forgotPasswordTextView.setOnClickListener {
                        // Intent to start the ForgotPasswordActivity
                        val intent = Intent(this, ForgetPassword::class.java)
                        startActivity(intent)
                }

                val signupTextView: TextView = findViewById(R.id.signupTextView)
                signupTextView.setOnClickListener {
                        // Intent to start the SignUp activity
                        val intent = Intent(this, SignUp::class.java)
                        startActivity(intent)
                }
        }

        private fun login(email: String, password: String) {
                val url = "http://172.16.48.199/Tutorial/login_project.php"
                val stringRequest = object : StringRequest(Request.Method.POST, url,
                        Response.Listener { response ->
                                // Handle response from PHP file
                                if (response.trim() == "Invalid email or password") {
                                        // Login failed, display error message
                                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                                } else {
                                        // Login successful, navigate to HomeActivity
                                        val intent = Intent(this, HomeActivity::class.java)
                                        intent.putExtra("username", response) // Pass username to HomeActivity
                                        startActivity(intent)
                                }
                        },
                        Response.ErrorListener { error ->
                                // Log error message
                                Log.e("Volley Error", error?.message ?: "Unknown error")
                                // Display error message
                                Toast.makeText(this, "Error: ${error?.message}", Toast.LENGTH_SHORT).show()
                        }) {
                        override fun getParams(): Map<String, String> {
                                val params = HashMap<String, String>()
                                params["email"] = email
                                params["password"] = password
                                return params
                        }
                }
                Volley.newRequestQueue(this).add(stringRequest)
        }
}
