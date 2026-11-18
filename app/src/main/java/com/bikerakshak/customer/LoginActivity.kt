package com.bikerakshak.customer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bikerakshak.customer.data.SessionManager

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvSignup = findViewById<TextView>(R.id.tvGoSignup)
        val tvForgot = findViewById<TextView>(R.id.tvForgot)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString()
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Demo login — real Supabase auth replaces this later.
            val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            SessionManager.signIn(this, name, email, "")
            Toast.makeText(this, "Welcome back, $name", Toast.LENGTH_SHORT).show()
            finish()
        }
        tvSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        tvForgot.setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
