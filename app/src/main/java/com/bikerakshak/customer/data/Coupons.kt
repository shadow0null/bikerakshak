package com.bikerakshak.customer.data

object Coupons {
    data class Coupon(val code: String, val percent: Int = 0, val flat: Int = 0, val minOrder: Int = 0)

    val all = listOf(
        Coupon("WELCOME10", percent = 10),
        Coupon("RAKSHAK50", flat = 50, minOrder = 499)
    )

    fun find(code: String): Coupon? =
        all.firstOrNull { it.code.equals(code.trim(), ignoreCase = true) }

    fun discountFor(coupon: Coupon, base: Int): Int {
        if (base < coupon.minOrder) return 0
        val d = if (coupon.percent > 0) base * coupon.percent / 100 else coupon.flat
        return minOf(d, base)
    }
}
