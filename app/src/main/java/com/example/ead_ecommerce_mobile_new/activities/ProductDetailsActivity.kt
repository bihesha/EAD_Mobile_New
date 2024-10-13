package com.example.ead_ecommerce_mobile_new.activities

import Product
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.R
import com.example.ead_ecommerce_mobile_new.api.Product.ProductApiService
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityProductDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val productApiService: ProductApiService = RetrofitInstance.productApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("productId")

        // Logger for productId
        Log.d(TAG, "Received productId: $productId")

        if (productId != null) {
            fetchProductDetails(productId)
        } else {
            Toast.makeText(this, "Product ID not found", Toast.LENGTH_SHORT).show()
        }

        // Back button in the toolbar or header
        binding.btnBackViewProfile.setOnClickListener {
            finish() // This will close the activity and go back to the previous screen
        }

        binding.btnBackToProducts.setOnClickListener {
            finish()
        }
    }

    private fun fetchProductDetails(productId: String) {
        // Fetch product details from the API
        productApiService.getProductById(productId).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful && response.body() != null) {
                    val product = response.body()!!
                    displayProductDetails(product)
                } else {
                    Toast.makeText(this@ProductDetailActivity, "Failed to fetch product", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("ProductDetailActivity", "API call failed", t)
                Toast.makeText(this@ProductDetailActivity, "Error fetching product: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayProductDetails(product: Product) {
        binding.apply {
            productName.text = product.productName
            productCategory.text = product.productCategory
            productDescription.text = product.productDescription
            productPrice.text = "Price: ${product.productPrice}"
            productAvailability.text = if (product.productAvailability) "Available" else "Unavailable"

            // Use Glide to load the product image
            Glide.with(this@ProductDetailActivity)
                .load(product.productImage) // URL of the product image
                .placeholder(R.drawable.ic_launcher_foreground) // Default placeholder image
                .error(R.drawable.ic_launcher_background) // Default error image
                .into(productImage) // Bind to the ImageView in the layout
        }
    }
}

