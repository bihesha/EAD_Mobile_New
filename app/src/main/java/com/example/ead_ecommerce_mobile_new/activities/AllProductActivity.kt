package com.example.ead_ecommerce_mobile_new.activities

import Product
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ead_ecommerce_mobile_new.adapter.ProductAdapter
import com.example.ead_ecommerce_mobile_new.api.Product.ProductApiService
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityAllProductsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.ead_ecommerce_mobile_new.models.ProductFetch

class AllProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllProductsBinding
    private lateinit var productAdapter: ProductAdapter
    private val productApiService: ProductApiService = RetrofitInstance.productApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch all products
        fetchAllProducts()
    }

    private fun fetchAllProducts() {
        productApiService.getAllProducts().enqueue(object : Callback<List<ProductFetch>> {
            override fun onResponse(call: Call<List<ProductFetch>>, response: Response<List<ProductFetch>>) {
                if (response.isSuccessful && response.body() != null) {
                    val productFetchList = response.body()!!
                    setupRecyclerView(productFetchList) // Passing ProductFetch list to the adapter
                } else {
                    Toast.makeText(this@AllProductActivity, "Failed to fetch products", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProductFetch>>, t: Throwable) {
                Toast.makeText(this@AllProductActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setupRecyclerView(products: List<ProductFetch>) {
        productAdapter = ProductAdapter(products) { selectedProduct ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("productId", selectedProduct.productId)
            startActivity(intent)
        }
        binding.productRecyclerView.adapter = productAdapter
    }
}
