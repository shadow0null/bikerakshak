package com.bikerakshak.customer.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class UserVehicle(
    val id: String,
    val name: String,
    val number: String
)

object VehicleStore {
    private const val PREFS = "bikerakshak_prefs"
    private const val KEY = "vehicles_json"

    fun getAll(context: Context): List<UserVehicle> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY, null) ?: return emptyList()
        val out = mutableListOf<UserVehicle>()
        val arr = JSONArray(raw)
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            out.add(UserVehicle(o.optString("id"), o.optString("name"), o.optString("number")))
        }
        return out
    }

    fun add(context: Context, vehicle: UserVehicle) {
        val list = getAll(context).toMutableList()
        list.add(vehicle)
        val arr = JSONArray()
        for (v in list) {
            arr.put(JSONObject().apply {
                put("id", v.id)
                put("name", v.name)
                put("number", v.number)
            })
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY, arr.toString()).apply()
    }

    fun clearAll(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().remove(KEY).apply()
    }
}
