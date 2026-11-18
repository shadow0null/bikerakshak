package com.bikerakshak.customer.data

import android.content.Context

/**
 * Demo session. Any valid email + 6+ char password signs in locally.
 * Real auth (Supabase GoTrue) replaces this in a later screen — the
 * SessionManager API (isLoggedIn / name / email / phone / logout) stays the same.
 */
object SessionManager {
    private const val PREFS = "bikerakshak_prefs"

    fun isLoggedIn(context: Context): Boolean =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean("logged_in", false)

    fun signIn(context: Context, name: String, email: String, phone: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putBoolean("logged_in", true)
            .putString("user_name", name)
            .putString("user_email", email)
            .putString("user_phone", phone)
            .apply()
    }

    fun name(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString("user_name", "Guest User") ?: "Guest User"

    fun email(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString("user_email", "") ?: ""

    fun phone(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString("user_phone", "") ?: ""

    fun updateProfile(context: Context, name: String, phone: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putString("user_name", name)
            .putString("user_phone", phone)
            .apply()
    }

    fun logout(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putBoolean("logged_in", false)
            .remove("user_name").remove("user_email").remove("user_phone")
            .apply()
    }

    fun deleteAccount(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().clear().apply()
        BookingRepository.clearAll(context)
        AddressStore.clearAll(context)
        VehicleStore.clearAll(context)
        CartManager.clear()
    }
}
