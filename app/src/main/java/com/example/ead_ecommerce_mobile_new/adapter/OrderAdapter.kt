package com.example.ead_ecommerce_mobile_new.adapter

import Order
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ead_ecommerce_mobile_new.databinding.ItemOrderBinding

class OrderAdapter(
    private val orders: List<Order>,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.apply {
                orderId.text = order.id
                orderNumber.text = order.orderNumber
                orderTotalPrice.text = "Total: ${order.totalPrice}"
                orderStatus.text = order.orderStatus
                deliveryStatus.text = order.deliveryStatus
                orderDate.text = order.orderDate.toString()
            }
            itemView.setOnClickListener {
                onItemClick(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int = orders.size
}
