package com.bikerakshak.customer.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class Booking(
    val id: String,
    val services: String,
    val total: Int,
    val date: String,
    val slot: String,
    val city: String,
    val address: String,
    val status: String
)

object BookingRepository {
    private const val PREFS = "bikerakshak_prefs"
    private const val KEY = "bookings_json"

    fun addBooking(context: Context, booking: Booking) {
        val list = getBookings(context).toMutableList()
        list.add(0, booking)
        save(context, list)
    }

    fun getBookings(context: Context): List<Booking> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY, null) ?: return emptyList()
        val out = mutableListOf<Booking>()
        val arr = JSONArray(raw)
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            out.add(
                Booking(
                    id = o.optString("id"),
                    services = o.optString("services"),
                    total = o.optInt("total"),
                    date = o.optString("date"),
                    slot = o.optString("slot"),
                    city = o.optString("city"),
                    address = o.optString("address"),
                    status = o.optString("status", "Pending")
                )
            )
        }
        return out
    }

    fun clearAll(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().remove(KEY).apply()
    }

    private fun save(context: Context, list: List<Booking>) {
        val arr = JSONArray()
        for (b in list) {
            arr.put(
                JSONObject().apply {
                    put("id", b.id)
                    put("services", b.services)
                    put("total", b.total)
                    put("date", b.date)
                    put("slot", b.slot)
                    put("city", b.city)
                    put("address", b.address)
                    put("status", b.status)
                }
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY, arr.toString()).apply()
    }
}
