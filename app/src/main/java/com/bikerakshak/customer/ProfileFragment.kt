package com.bikerakshak.customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bikerakshak.customer.data.SessionManager

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val tvName = view.findViewById<TextView>(R.id.tvProfileName)
        val tvEmail = view.findViewById<TextView>(R.id.tvProfileEmail)
        val btnLogin = view.findViewById<Button>(R.id.btnGoLogin)
        val btnEdit = view.findViewById<Button>(R.id.btnEditProfile)
        val btnAddresses = view.findViewById<Button>(R.id.btnSavedAddresses)
        val btnManage = view.findViewById<Button>(R.id.btnManageAccount)

        val loggedIn = SessionManager.isLoggedIn(requireContext())
        tvName.text = if (loggedIn) SessionManager.name(requireContext()) else "Guest User"
        tvEmail.text = if (loggedIn) SessionManager.email(requireContext()) else "Login to book faster"
        btnLogin.visibility = if (loggedIn) View.GONE else View.VISIBLE
        btnEdit.isEnabled = loggedIn
        btnManage.isEnabled = loggedIn

        btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        btnEdit.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
        btnAddresses.setOnClickListener {
            startActivity(Intent(requireContext(), SavedAddressesActivity::class.java))
        }
        btnManage.setOnClickListener {
            startActivity(Intent(requireContext(), ManageAccountActivity::class.java))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        view?.let { v ->
            val loggedIn = SessionManager.isLoggedIn(requireContext())
            v.findViewById<TextView>(R.id.tvProfileName).text =
                if (loggedIn) SessionManager.name(requireContext()) else "Guest User"
            v.findViewById<TextView>(R.id.tvProfileEmail).text =
                if (loggedIn) SessionManager.email(requireContext()) else "Login to book faster"
            v.findViewById<Button>(R.id.btnGoLogin).visibility =
                if (loggedIn) View.GONE else View.VISIBLE
            v.findViewById<Button>(R.id.btnEditProfile).isEnabled = loggedIn
            v.findViewById<Button>(R.id.btnManageAccount).isEnabled = loggedIn
        }
    }
}
