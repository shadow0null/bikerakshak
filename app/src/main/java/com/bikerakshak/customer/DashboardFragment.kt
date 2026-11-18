package com.bikerakshak.customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        view.findViewById<Button>(R.id.btnBookService).setOnClickListener {
            startActivity(Intent(requireContext(), BikeServiceActivity::class.java))
        }
        return view
    }
}
