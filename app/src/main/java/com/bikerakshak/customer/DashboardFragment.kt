package com.bikerakshak.customer

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val openCatalogue = {
            startActivity(Intent(requireContext(), BikeServiceActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnBookService).setOnClickListener { openCatalogue() }
        view.findViewById<Button>(R.id.btnOfferBook).setOnClickListener { openCatalogue() }
        view.findViewById<TextView>(R.id.cardService2Wheeler).setOnClickListener { openCatalogue() }
        view.findViewById<TextView>(R.id.cardPuncture).setOnClickListener { openCatalogue() }
        view.findViewById<TextView>(R.id.cardJumpStart).setOnClickListener { openCatalogue() }
        view.findViewById<TextView>(R.id.cardBattery).setOnClickListener { openCatalogue() }
        view.findViewById<Button>(R.id.btnHelp).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Help & Support")
                .setMessage("Call or WhatsApp support: +91 99051 66009\nEmail: support@studydesk.fun\nAvailable 24x7 for emergencies.")
                .setPositiveButton("OK", null)
                .show()
        }
        return view
    }
}
