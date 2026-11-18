package com.bikerakshak.customer

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
        val tvToggle = findViewById<TextView>(R.id.tvTogglePassword)

        tvToggle.setOnClickListener {
            val showing = etPassword.inputType and InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD != 0
            etPassword.inputType = if (showing) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            tvToggle.text = if (showing) "Show" else "Hide"
            etPassword.setSelection(etPassword.text.length)
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
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
        findViewById<TextView>(R.id.tvGoSignup).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        findViewById<TextView>(R.id.tvForgot).setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }
        findViewById<android.view.View>(R.id.btnGoogle).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Continue with Google")
                .setMessage("Google sign-in activates once BikeRakshak is connected to Supabase. For now, please sign in with email.")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
