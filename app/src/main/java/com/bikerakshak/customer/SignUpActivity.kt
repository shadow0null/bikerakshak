package com.bikerakshak.customer

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
        val etConfirm = findViewById<EditText>(R.id.etConfirmPassword)

        setupToggle(etPassword, findViewById(R.id.tvTogglePassword))
        setupToggle(etConfirm, findViewById(R.id.tvToggleConfirm))

        findViewById<Button>(R.id.btnSignup).setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val pass = etPassword.text.toString()
            val confirm = etConfirm.text.toString()
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
            if (pass != confirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Demo sign-up — real Supabase auth replaces this later.
            SessionManager.signIn(this, name, email, phone)
            Toast.makeText(this, "Account created. Welcome, $name!", Toast.LENGTH_SHORT).show()
            finish()
        }
        findViewById<TextView>(R.id.tvGoSignin).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        findViewById<android.view.View>(R.id.btnGoogle).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Continue with Google")
                .setMessage("Google sign-in activates once BikeRakshak is connected to Supabase. For now, please create your account with email.")
                .setPositiveButton("OK", null)
                .show()
        }
        findViewById<TextView>(R.id.tvTerms).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Terms of Service")
                .setMessage("1. BikeRakshak provides doorstep two-wheeler repair in East Champaran only.\n2. Prices shown are starting estimates; the mechanic confirms the final price before work begins.\n3. Payment is after service (UPI / cash).\n4. Genuine parts only; old parts are handed back to you.\n5. Labour warranty: 15 days (general), 30 days (half overhaul), 60 days (full overhaul).")
                .setPositiveButton("OK", null)
                .show()
        }
        findViewById<TextView>(R.id.tvPrivacy).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Privacy Policy")
                .setMessage("We collect only what a booking needs: your name, phone, address and vehicle details. Your data is never sold. Location is used only to find your service address. You can delete your account and data anytime from Manage Account.")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun setupToggle(field: EditText, toggle: ImageView) {
        var showing = false
        toggle.setOnClickListener {
            showing = !showing
            field.inputType = if (showing) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            toggle.setImageResource(if (showing) R.drawable.ic_eye else R.drawable.ic_eye_off)
            field.setSelection(field.text.length)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
