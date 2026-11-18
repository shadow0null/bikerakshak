package com.bikerakshak.customer.data

/**
 * Service catalogue for BikeRakshak (two-wheelers only, East Champaran).
 * Prices are Week-1 survey STARTING estimates for 0-249cc, not locked rates.
 * Higher CC ranges multiply the base price (see priceFor).
 */
data class Service(
    val id: String,
    val name: String,
    val desc: String,
    val basePrice: Int
)

object ServiceCatalogue {
    val services = listOf(
        Service("general", "General Service", "21-point check, chain lube, spark plug clean, air filter, brake adjust", 499),
        Service("engine-oil", "Service with Engine Oil", "General service + engine oil change (oil extra at MRP)", 799),
        Service("diagnosis", "Diagnosis", "Full check-up to find the fault before any repair", 199),
        Service("jump-start", "Jump Start", "Dead battery jump-start at your doorstep", 249),
        Service("puncture", "Doorstep Puncture Repair", "Tube / tubeless puncture fixed where you are", 199),
        Service("running-repair", "Running Repair", "On-road fix for breakdowns — get moving again", 249),
        Service("half-overhaul", "Engine Half Overhaul", "Top-end engine work, 30-day warranty", 3499),
        Service("full-overhaul", "Engine Full Overhaul", "Complete engine rebuild, 60-day warranty", 5999),
        Service("carburettor", "Carburettor Cleaning", "Cleaning + tuning for smooth pickup", 249),
        Service("obd", "OBD Scanner Inspection", "Scanner diagnosis for BS6 bikes", 199),
        Service("battery", "Battery Replacement", "Labour only — Exide / Amaron battery at MRP", 99),
        Service("brake-pads", "Disc Brake Pad Replacement", "Labour only — pads at MRP, old pads handed back", 249),
        Service("chain-sprocket", "Chain & Sprocket Replacement", "Labour only — kit at MRP", 299),
        Service("pick-drop", "Pick & Drop", "We pick your bike up, service it, drop it back", 199),
        Service("ev-service", "Electric Scooter General Service", "General check for EV scooters", 699),
        Service("petrol", "Petrol Delivery", "Sealed-can petrol delivered, pump rate + delivery", 199),
        Service("drive-belt", "Scooty Drive-Belt Replacement", "Labour only — belt at MRP", 299),
        Service("night-diagnosis", "Night Not-Starting Diagnosis", "24x7 night call-out diagnosis (night charge applies)", 249)
    )

    val ccRanges = listOf("Up to 249cc", "250-399cc", "400-599cc", "600cc+", "Electric")

    fun multiplierFor(ccRange: String): Double = when (ccRange) {
        "250-399cc" -> 1.3
        "400-599cc" -> 1.6
        "600cc+" -> 2.0
        else -> 1.0
    }

    fun priceFor(service: Service, ccRange: String): Int {
        if (ccRange == "Electric" && service.id != "ev-service") return service.basePrice
        return (service.basePrice * multiplierFor(ccRange)).toInt()
    }

    fun byId(id: String): Service? = services.firstOrNull { it.id == id }
}
