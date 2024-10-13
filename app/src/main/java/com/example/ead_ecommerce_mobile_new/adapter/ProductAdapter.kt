package com.example.ead_ecommerce_mobile_new.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

                Log.d("ProductImage", "Image URL: ${product.productImage}")

                // Use Glide to load the product image from the URL
                Glide.with(binding.productImage.context)
                    .load(product.productImage)
                    .into(productImage)

                // Use the correct ID for the checkbox
                productCheckBox.isChecked = selectedProducts.contains(product)

                // Handle checkbox check/uncheck event
                productCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedProducts.add(product)
                    } else {
                        selectedProducts.remove(product)
                    }
                }
            }

            // Optionally, you can keep the click listener for the entire itemView
            itemView.setOnClickListener {
                if (selectedProducts.contains(product)) {
                    selectedProducts.remove(product)
                    binding.productCheckBox.isChecked = false
                } else {
                    selectedProducts.add(product)
                    binding.productCheckBox.isChecked = true
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
