package com.bikerakshak.customer

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bikerakshak.customer.data.AddressStore
import com.bikerakshak.customer.data.Towns
import com.bikerakshak.customer.data.UserAddress
import java.util.UUID

class SavedAddressesActivity : AppCompatActivity() {

    private lateinit var adapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_addresses)
        supportActionBar?.title = "Saved Addresses"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etLabel = findViewById<EditText>(R.id.etLabel)
        val etHouse = findViewById<EditText>(R.id.etHouse)
        val etStreet = findViewById<EditText>(R.id.etStreet)
        val etArea = findViewById<EditText>(R.id.etArea)
        val spCity = findViewById<Spinner>(R.id.spCity)
        spCity.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Towns.all)

        val rv = findViewById<RecyclerView>(R.id.rvAddresses)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = AddressAdapter(
            onDefault = { id -> AddressStore.setDefault(this, id); refresh() },
            onDelete = { id -> AddressStore.delete(this, id); refresh() }
        )
        rv.adapter = adapter

        findViewById<Button>(R.id.btnAddAddress).setOnClickListener {
            val label = etLabel.text.toString().trim().ifEmpty { "Home" }
            val house = etHouse.text.toString().trim()
            val street = etStreet.text.toString().trim()
            val area = etArea.text.toString().trim()
            if (house.isEmpty() || street.isEmpty() || area.isEmpty()) {
                Toast.makeText(this, "Fill house, street and area", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AddressStore.add(
                this,
                UserAddress(
                    id = UUID.randomUUID().toString(),
                    label = label,
                    house = house,
                    street = street,
                    area = area,
                    city = spCity.selectedItem.toString(),
                    isDefault = AddressStore.getAll(this).isEmpty()
                )
            )
            etHouse.text.clear()
            etStreet.text.clear()
            etArea.text.clear()
            Toast.makeText(this, "Address saved", Toast.LENGTH_SHORT).show()
            refresh()
        }
        refresh()
    }

    private fun refresh() {
        adapter.submitList(AddressStore.getAll(this))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

class AddressAdapter(
    private val onDefault: (String) -> Unit,
    private val onDelete: (String) -> Unit
) : RecyclerView.Adapter<AddressAdapter.VH>() {

    private var list: List<UserAddress> = emptyList()

    class VH(v: android.view.View) : RecyclerView.ViewHolder(v) {
        val label: android.widget.TextView = v.findViewById(R.id.tvAddressLabel)
        val full: android.widget.TextView = v.findViewById(R.id.tvAddressFull)
        val btnDefault: Button = v.findViewById(R.id.btnSetDefault)
        val btnDelete: Button = v.findViewById(R.id.btnDeleteAddress)
    }

    fun submitList(newList: List<UserAddress>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): VH {
        val v = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_address, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val a = list[position]
        holder.label.text = if (a.isDefault) "${a.label}  •  Default" else a.label
        holder.full.text = a.full()
        holder.btnDefault.isEnabled = !a.isDefault
        holder.btnDefault.setOnClickListener { onDefault(a.id) }
        holder.btnDelete.setOnClickListener { onDelete(a.id) }
    }
}
