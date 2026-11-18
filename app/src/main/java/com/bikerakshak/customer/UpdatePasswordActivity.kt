package com.bikerakshak.customer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Opened by the password-reset deep link: bikerakshak://reset
 * (also reachable from the Login screen's "Forgot password" link in demo mode).
 */
class UpdatePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)
        supportActionBar?.title = "Reset Password"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etNew = findViewById<EditText>(R.id.etNewPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPassword)
        val btnSave = findViewById<Button>(R.id.btnSavePassword)

        btnSave.setOnClickListener {
            val a = etNew.text.toString()
            val b = etConfirm.text.toString()
            if (a.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (a != b) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Password updated. Please login with your new password.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
