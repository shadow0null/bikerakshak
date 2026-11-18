package com.bikerakshak.customer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bikerakshak.customer.data.SessionManager

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.title = "Create Account"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val pass = etPassword.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phone.length < 10) {
                Toast.makeText(this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Demo sign-up — real Supabase auth replaces this later.
            SessionManager.signIn(this, name, email, phone)
            Toast.makeText(this, "Account created. Welcome, $name!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
