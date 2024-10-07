package com.example.ead_ecommerce_mobile_new.adapter

import Product
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ead_ecommerce_mobile_new.databinding.ItemProductBinding
import com.example.ead_ecommerce_mobile_new.models.ProductFetch

class ProductAdapter(
    private val products: List<ProductFetch>,
    private val onItemClick: (ProductFetch) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductFetch) {
            binding.apply {
                productId.text = product.productId // Assuming you want to display the id as a string
                productName.text = product.productName
                productCategory.text = product.productCategory
                productDescription.text = product.productDescription
                productPrice.text = "Price: ${product.productPrice}"
                productAvailability.text = if (product.productAvailability) "Available" else "Unavailable"
            }
            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size
}
