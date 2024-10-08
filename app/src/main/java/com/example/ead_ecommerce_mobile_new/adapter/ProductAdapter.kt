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

    private val selectedProducts = mutableListOf<ProductFetch>()

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductFetch) {
            binding.apply {

                productName.text = product.productName
                productCategory.text = product.productCategory
                productDescription.text = product.productDescription
                productPrice.text = "Price: ${product.productPrice}"
                productAvailability.text = if (product.productAvailability) "Available" else "Unavailable"
            }
            itemView.setOnClickListener {
                if (selectedProducts.contains(product)) {
                    selectedProducts.remove(product)
                } else {
                    selectedProducts.add(product)
                }
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

    fun getSelectedProducts(): List<ProductFetch> = selectedProducts
}
