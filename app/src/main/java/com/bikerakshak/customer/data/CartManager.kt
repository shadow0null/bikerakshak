package com.bikerakshak.customer.data

object CartManager {
    private val items = LinkedHashMap<String, Int>()

    fun add(serviceId: String) {
        items[serviceId] = (items[serviceId] ?: 0) + 1
    }

    fun remove(serviceId: String) {
        val q = (items[serviceId] ?: 0) - 1
        if (q <= 0) items.remove(serviceId) else items[serviceId] = q
    }

    fun qty(serviceId: String): Int = items[serviceId] ?: 0

    fun count(): Int = items.values.sum()

    fun entries(): List<Pair<Service, Int>> =
        items.mapNotNull { (id, q) -> ServiceCatalogue.byId(id)?.let { it to q } }

    /** Base total (0-249cc prices). The booking screen re-prices by CC range. */
    fun baseTotal(): Int = entries().sumOf { (s, q) -> s.basePrice * q }

    fun isEmpty(): Boolean = items.isEmpty()

    fun clear() = items.clear()
}
