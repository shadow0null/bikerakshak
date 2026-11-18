package com.bikerakshak.customer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bikerakshak.customer.data.SessionManager

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        etName.setText(SessionManager.name(this))
        etPhone.setText(SessionManager.phone(this))

        findViewById<Button>(R.id.btnSaveProfile).setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            if (name.isEmpty() || phone.length < 10) {
                Toast.makeText(this, "Enter your name and a valid 10-digit phone", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            SessionManager.updateProfile(this, name, phone)
            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
