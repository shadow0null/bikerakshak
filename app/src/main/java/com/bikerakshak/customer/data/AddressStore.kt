package com.bikerakshak.customer.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class UserAddress(
    val id: String,
    val label: String,
    val house: String,
    val street: String,
    val area: String,
    val city: String,
    val isDefault: Boolean
) {
    fun full(): String = "$house, $street, $area, $city"
}

/** Launch towns — bookings outside these are not serviceable yet. */
object Towns {
    val all = listOf(
        "Motihari", "Dhaka", "Raxaul", "Chakia", "Pakridayal",
        "Kesaria", "Areraj", "Mehsi", "Sugauli", "Harsidhi",
        "Turkaulia", "Patahi"
    )
    const val OUTSIDE = "Outside East Champaran"
}

object AddressStore {
    private const val PREFS = "bikerakshak_prefs"
    private const val KEY = "addresses_json"

    fun getAll(context: Context): List<UserAddress> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY, null) ?: return emptyList()
        val out = mutableListOf<UserAddress>()
        val arr = JSONArray(raw)
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            out.add(
                UserAddress(
                    id = o.optString("id"),
                    label = o.optString("label"),
                    house = o.optString("house"),
                    street = o.optString("street"),
                    area = o.optString("area"),
                    city = o.optString("city"),
                    isDefault = o.optBoolean("isDefault", false)
                )
            )
        }
        return out
    }

    fun default(context: Context): UserAddress? =
        getAll(context).firstOrNull { it.isDefault } ?: getAll(context).firstOrNull()

    fun add(context: Context, address: UserAddress) {
        val list = getAll(context).toMutableList()
        if (address.isDefault) {
            for (i in list.indices) list[i] = list[i].copy(isDefault = false)
        }
        list.add(address)
        save(context, list)
    }

    fun delete(context: Context, id: String) {
        save(context, getAll(context).filter { it.id != id })
    }

    fun setDefault(context: Context, id: String) {
        val list = getAll(context).map { it.copy(isDefault = it.id == id) }
        save(context, list)
    }

    fun clearAll(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().remove(KEY).apply()
    }

    private fun save(context: Context, list: List<UserAddress>) {
        val arr = JSONArray()
        for (a in list) {
            arr.put(
                JSONObject().apply {
                    put("id", a.id)
                    put("label", a.label)
                    put("house", a.house)
                    put("street", a.street)
                    put("area", a.area)
                    put("city", a.city)
                    put("isDefault", a.isDefault)
                }
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY, arr.toString()).apply()
    }
}
