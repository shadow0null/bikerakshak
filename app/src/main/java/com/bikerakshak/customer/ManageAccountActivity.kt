package com.bikerakshak.customer

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bikerakshak.customer.data.SessionManager

class ManageAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_account)
        supportActionBar?.title = "Manage Account"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        if (!SessionManager.isLoggedIn(this)) {
            // Guest mode: no account to log out of — offer sign-in instead.
            btnLogout.text = "Login / Sign Up"
            btnLogout.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        } else {
            btnLogout.setOnClickListener {
                SessionManager.logout(this)
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                })
                finish()
            }
        }

        findViewById<Button>(R.id.btnDeleteAccount).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete account?")
                .setMessage("This removes your profile, saved addresses and booking history from this phone. This cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    SessionManager.deleteAccount(this)
                    Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
