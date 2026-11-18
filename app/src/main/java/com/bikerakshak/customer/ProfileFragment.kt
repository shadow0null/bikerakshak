package com.bikerakshak.customer

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bikerakshak.customer.data.SessionManager
import com.bikerakshak.customer.data.UserVehicle
import com.bikerakshak.customer.data.VehicleStore
import java.util.UUID

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<Button>(R.id.btnGoLogin).setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnSavedAddresses).setOnClickListener {
            startActivity(Intent(requireContext(), SavedAddressesActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnManageAccount).setOnClickListener {
            startActivity(Intent(requireContext(), ManageAccountActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnHelpSupport).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Help & Support")
                .setMessage("Call or WhatsApp support: +91 99051 66009\nEmail: support@studydesk.fun\nAvailable 24x7 for emergencies.")
                .setPositiveButton("OK", null)
                .show()
        }
        view.findViewById<Button>(R.id.btnTerms).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Terms of Service")
                .setMessage("1. BikeRakshak provides doorstep two-wheeler repair in East Champaran only.\n2. Prices shown are starting estimates; the mechanic confirms the final price before work begins.\n3. Payment is after service (UPI / cash).\n4. Genuine parts only; old parts are handed back to you.\n5. Labour warranty: 15 days (general), 30 days (half overhaul), 60 days (full overhaul).")
                .setPositiveButton("OK", null)
                .show()
        }
        view.findViewById<Button>(R.id.btnPrivacy).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Privacy Policy")
                .setMessage("We collect only what a booking needs: your name, phone, address and vehicle details. Your data is never sold. Location is used only to find your service address. You can delete your account and data anytime from Manage Account.")
                .setPositiveButton("OK", null)
                .show()
        }
        view.findViewById<Button>(R.id.btnShare).setOnClickListener {
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "BikeRakshak — Bike Ruki? Rakshak Aaya. 24x7 two-wheeler mechanic service in East Champaran, Bihar.")
            }
            startActivity(Intent.createChooser(send, "Share BikeRakshak"))
        }
        view.findViewById<Button>(R.id.btnRate).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Rate the App")
                .setMessage("Rating opens on Google Play once BikeRakshak is published there. Thanks for the love in advance!")
                .setPositiveButton("OK", null)
                .show()
        }
        view.findViewById<TextView>(R.id.btnAddVehicle).setOnClickListener {
            val ctx = requireContext()
            val layout = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(48, 24, 48, 8)
            }
            val etName = EditText(ctx).apply { hint = "Vehicle (e.g. Hero Splendor)" }
            val etNumber = EditText(ctx).apply { hint = "Number plate (e.g. BR05 AB 1234)" }
            layout.addView(etName)
            layout.addView(etNumber)
            AlertDialog.Builder(ctx)
                .setTitle("Add Vehicle")
                .setView(layout)
                .setPositiveButton("Save") { _, _ ->
                    val name = etName.text.toString().trim()
                    val number = etNumber.text.toString().trim()
                    if (name.isEmpty() || number.isEmpty()) {
                        Toast.makeText(ctx, "Enter vehicle name and number", Toast.LENGTH_SHORT).show()
                    } else {
                        VehicleStore.add(ctx, UserVehicle(UUID.randomUUID().toString(), name, number))
                        Toast.makeText(ctx, "Vehicle added", Toast.LENGTH_SHORT).show()
                        refreshVehicles(view)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        view?.let { refresh(it) }
    }

    private fun refresh(v: View) {
        val ctx = requireContext()
        val loggedIn = SessionManager.isLoggedIn(ctx)
        v.findViewById<TextView>(R.id.tvProfileName).text =
            if (loggedIn) SessionManager.name(ctx) else "Guest User"
        v.findViewById<TextView>(R.id.tvProfileEmail).text =
            if (loggedIn) SessionManager.email(ctx) else "Login to book faster"
        v.findViewById<TextView>(R.id.tvAvatar).text =
            if (loggedIn) SessionManager.name(ctx).take(2).uppercase() else "BR"
        v.findViewById<Button>(R.id.btnGoLogin).visibility =
            if (loggedIn) View.GONE else View.VISIBLE
        v.findViewById<Button>(R.id.btnEditProfile).isEnabled = loggedIn
        v.findViewById<Button>(R.id.btnManageAccount).isEnabled = loggedIn
        refreshVehicles(v)
    }

    private fun refreshVehicles(v: View) {
        val vehicles = VehicleStore.getAll(requireContext())
        v.findViewById<TextView>(R.id.tvVehicles).text =
            if (vehicles.isEmpty()) "No vehicles added yet"
            else vehicles.joinToString("\n") { "${it.name}  •  ${it.number}" }
    }
}
