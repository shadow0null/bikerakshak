package com.bikerakshak.customer

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bikerakshak.customer.data.AddressStore
import com.bikerakshak.customer.data.Booking
import com.bikerakshak.customer.data.BookingRepository
import com.bikerakshak.customer.data.CartManager
import com.bikerakshak.customer.data.Coupons
import com.bikerakshak.customer.data.ServiceCatalogue
import com.bikerakshak.customer.data.SessionManager
import com.bikerakshak.customer.data.Towns
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Calendar
import java.util.UUID

class BookingActivity : AppCompatActivity() {

    private val slots = listOf(
        "8-9 AM", "9-10 AM", "10-11 AM", "11-12 PM",
        "12-1 PM", "1-2 PM", "2-3 PM", "3-4 PM",
        "4-5 PM", "5-6 PM", "6-7 PM", "7-8 PM"
    )
    private var selectedDate = ""
    private var selectedSlot = ""
    private var appliedCoupon: Coupons.Coupon? = null
    private lateinit var tvSummary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        supportActionBar?.title = "Book Service"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (CartManager.isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etIssue = findViewById<EditText>(R.id.etIssue)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val etCoupon = findViewById<EditText>(R.id.etCoupon)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val chipGroup = findViewById<ChipGroup>(R.id.chipSlots)
        val btnCoupon = findViewById<Button>(R.id.btnApplyCoupon)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        tvSummary = findViewById(R.id.tvSummary)

        if (SessionManager.isLoggedIn(this)) {
            etName.setText(SessionManager.name(this))
            etPhone.setText(SessionManager.phone(this))
        }
        AddressStore.default(this)?.let { etAddress.setText(it.full()) }

        val spCc = findViewById<Spinner>(R.id.spCc)
        spCc.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ServiceCatalogue.ccRanges)
        spCc.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: android.widget.AdapterView<*>?, v: View?, pos: Int, id: Long) = refreshSummary()
            override fun onNothingSelected(p: android.widget.AdapterView<*>?) {}
        }

        val spCity = findViewById<Spinner>(R.id.spCity)
        val cities = Towns.all + Towns.OUTSIDE
        spCity.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)

        tvDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "%02d/%02d/%04d".format(d, m + 1, y)
                tvDate.text = selectedDate
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).apply {
                datePicker.minDate = c.timeInMillis
                show()
            }
        }

        for (s in slots) {
            val chip = Chip(this).apply {
                text = s
                isCheckable = true
            }
            chip.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedSlot = s
            }
            chipGroup.addView(chip)
        }

        btnCoupon.setOnClickListener {
            val code = etCoupon.text.toString()
            val c = Coupons.find(code)
            if (c == null) {
                Toast.makeText(this, "Invalid coupon code", Toast.LENGTH_SHORT).show()
                appliedCoupon = null
            } else {
                appliedCoupon = c
                Toast.makeText(this, "Coupon applied: ${c.code}", Toast.LENGTH_SHORT).show()
            }
            refreshSummary()
        }

        btnConfirm.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val issue = etIssue.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val city = spCity.selectedItem.toString()
            val cc = spCc.selectedItem.toString()

            if (name.isEmpty() || phone.length < 10) {
                Toast.makeText(this, "Enter your name and a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (city == Towns.OUTSIDE) {
                AlertDialog.Builder(this)
                    .setTitle("Not serviceable yet")
                    .setMessage("BikeRakshak currently serves East Champaran only (Motihari, Dhaka, Raxaul, Chakia, Pakridayal, Kesaria, Areraj, Mehsi, Sugauli, Harsidhi, Turkaulia, Patahi). We will expand soon.")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                Toast.makeText(this, "Enter your service address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Pick a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedSlot.isEmpty()) {
                Toast.makeText(this, "Pick a time slot", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val total = computeTotal(cc)
            val servicesText = CartManager.entries().joinToString(", ") { (s, q) ->
                if (q > 1) "${s.name} x$q" else s.name
            }
            val booking = Booking(
                id = UUID.randomUUID().toString().take(8).uppercase(),
                services = servicesText,
                total = total,
                date = selectedDate,
                slot = selectedSlot,
                city = city,
                address = address,
                status = "Pending"
            )
            BookingRepository.addBooking(this, booking)
            CartManager.clear()

            AlertDialog.Builder(this)
                .setTitle("Booking confirmed")
                .setMessage("Booking #${booking.id}\n$servicesText\n$selectedDate, $selectedSlot\nTotal ₹$total — pay after service (UPI / cash).\nOur mechanic will call you on $phone.")
                .setPositiveButton("View my orders") { _, _ -> finish() }
                .setCancelable(false)
                .show()
        }

        refreshSummary()
    }

    private fun baseTotal(cc: String): Int =
        CartManager.entries().sumOf { (s, q) -> ServiceCatalogue.priceFor(s, cc) * q }

    /** ₹49 service charge on jobs under ₹499. No visit fee. */
    private fun computeTotal(cc: String): Int {
        val base = baseTotal(cc)
        val charge = if (base < 499) 49 else 0
        val discount = appliedCoupon?.let { Coupons.discountFor(it, base) } ?: 0
        return base + charge - discount
    }

    private fun refreshSummary() {
        val spCc = findViewById<Spinner>(R.id.spCc)
        val cc = try { spCc.selectedItem.toString() } catch (e: Exception) { ServiceCatalogue.ccRanges[0] }
        val base = baseTotal(cc)
        val charge = if (base < 499) 49 else 0
        val discount = appliedCoupon?.let { Coupons.discountFor(it, base) } ?: 0
        val night = CartManager.entries().any { it.first.id == "night-diagnosis" }
        tvSummary.text = buildString {
            append("Services: ₹$base")
            append("\nService charge: ₹$charge")
            if (discount > 0) append("\nCoupon discount: -₹$discount")
            if (night) append("\nNight charge (10 PM - 6 AM): ₹149")
            append("\nTotal: ₹${base + charge - discount + if (night) 149 else 0}")
            append("\nPay after service — UPI / cash")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
