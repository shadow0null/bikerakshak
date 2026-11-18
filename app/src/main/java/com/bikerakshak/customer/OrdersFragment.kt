package com.bikerakshak.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bikerakshak.customer.data.Booking
import com.bikerakshak.customer.data.BookingRepository

class OrdersFragment : Fragment() {

    private lateinit var adapter: OrderAdapter
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        tvEmpty = view.findViewById(R.id.tvEmptyOrders)
        val rv = view.findViewById<RecyclerView>(R.id.rvOrders)
        rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = OrderAdapter()
        rv.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        val bookings = BookingRepository.getBookings(requireContext())
        adapter.submitList(bookings)
        tvEmpty.visibility = if (bookings.isEmpty()) View.VISIBLE else View.GONE
    }
}

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.VH>() {

    private var list: List<Booking> = emptyList()

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val id: TextView = v.findViewById(R.id.tvOrderId)
        val status: TextView = v.findViewById(R.id.tvOrderStatus)
        val services: TextView = v.findViewById(R.id.tvOrderServices)
        val whenText: TextView = v.findViewById(R.id.tvOrderWhen)
        val total: TextView = v.findViewById(R.id.tvOrderTotal)
        val step1: TextView = v.findViewById(R.id.tvStep1)
        val step2: TextView = v.findViewById(R.id.tvStep2)
        val step3: TextView = v.findViewById(R.id.tvStep3)
        val step4: TextView = v.findViewById(R.id.tvStep4)
    }

    fun submitList(newList: List<Booking>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val b = list[position]
        holder.id.text = "Booking #${b.id}"
        holder.status.text = b.status
        holder.services.text = b.services
        holder.whenText.text = "${b.date}  •  ${b.slot}  •  ${b.city}"
        holder.total.text = "Total ₹${b.total} — pay after service"
        // Timeline: Pending = step 1 done; Completed = all done.
        val done = if (b.status == "Completed") 4 else 1
        val steps = listOf(holder.step1 to "Placed", holder.step2 to "On the Way", holder.step3 to "In Progress", holder.step4 to "Completed")
        steps.forEachIndexed { i, (tv, label) ->
            val isDone = i < done
            tv.text = (if (isDone) "✓ " else "○ ") + label
            tv.setTextColor(if (isDone) 0xFF15803D.toInt() else 0xFF999999.toInt())
        }
    }
}
