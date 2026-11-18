package com.bikerakshak.customer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bikerakshak.customer.data.CartManager
import com.bikerakshak.customer.data.Service
import com.bikerakshak.customer.data.ServiceCatalogue

class BikeServiceActivity : AppCompatActivity() {

    private lateinit var adapter: ServiceAdapter
    private lateinit var tvCartTotal: TextView
    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_service)
        supportActionBar?.title = "Book a Service"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rv = findViewById<RecyclerView>(R.id.rvServices)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ServiceAdapter(ServiceCatalogue.services) { refreshCart() }
        rv.adapter = adapter

        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val q = s.toString().trim().lowercase()
                adapter.submitList(
                    if (q.isEmpty()) ServiceCatalogue.services
                    else ServiceCatalogue.services.filter {
                        it.name.lowercase().contains(q) || it.desc.lowercase().contains(q)
                    }
                )
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        tvCartTotal = findViewById(R.id.tvCartTotal)
        btnContinue = findViewById(R.id.btnContinue)
        btnContinue.setOnClickListener {
            if (CartManager.isEmpty()) {
                Toast.makeText(this, "Add at least one service first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, BookingActivity::class.java))
        }
        refreshCart()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        refreshCart()
    }

    private fun refreshCart() {
        val count = CartManager.count()
        tvCartTotal.text = if (count == 0) "Cart is empty"
        else "$count item${if (count > 1) "s" else ""}  •  ₹${CartManager.baseTotal()}"
        btnContinue.isEnabled = count > 0
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

class ServiceAdapter(
    private var list: List<Service>,
    private val onChanged: () -> Unit
) : RecyclerView.Adapter<ServiceAdapter.VH>() {

    class VH(v: android.view.View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.tvServiceName)
        val desc: TextView = v.findViewById(R.id.tvServiceDesc)
        val price: TextView = v.findViewById(R.id.tvServicePrice)
        val qty: TextView = v.findViewById(R.id.tvQty)
        val btnMinus: Button = v.findViewById(R.id.btnMinus)
        val btnPlus: Button = v.findViewById(R.id.btnPlus)
    }

    fun submitList(newList: List<Service>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): VH {
        val v = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = list[position]
        holder.name.text = s.name
        holder.desc.text = s.desc
        holder.price.text = "₹${s.basePrice} onwards"
        holder.qty.text = CartManager.qty(s.id).toString()
        holder.btnPlus.setOnClickListener {
            CartManager.add(s.id)
            holder.qty.text = CartManager.qty(s.id).toString()
            onChanged()
        }
        holder.btnMinus.setOnClickListener {
            CartManager.remove(s.id)
            holder.qty.text = CartManager.qty(s.id).toString()
            onChanged()
        }
    }
}
