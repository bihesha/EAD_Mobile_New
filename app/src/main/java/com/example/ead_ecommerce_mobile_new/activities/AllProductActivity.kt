package com.example.ead_ecommerce_mobile_new.activities

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
import java.text.SimpleDateFormat
import java.util.* // For UUID
import Order
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance.orderApiService

class AllProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllProductsBinding
    private lateinit var productAdapter: ProductAdapter
    private val productApiService: ProductApiService = RetrofitInstance.productApiService
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ProgressBar
        binding.progressBar.visibility = View.GONE // Make sure it's initially hidden

        // Fetch all products
        fetchAllProducts()

        // Set up create order button
        binding.createOrderButton.setOnClickListener {
            createOrder()
        }
    }

    private fun createOrder() {
        val selectedProducts = productAdapter.getSelectedProducts()

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
        val email = sharedPreferences.getString("email", null)

        // Create an order object
        if (selectedProducts.isNotEmpty()) {
            val order = Order(
                id = UUID.randomUUID().toString(),
                userId = userId ?: "",
                email = email ?: "",
                products = selectedProducts,
                totalPrice = selectedProducts.sumOf { it.productPrice },
                deliveryStatus = "Pending",
                orderStatus = "New",
                orderNumber = UUID.randomUUID().toString(),
                isCancel = false,
                cancellationNote = null,
                orderDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            )

            // Show loading indicator
            binding.progressBar.visibility = View.VISIBLE
            binding.createOrderButton.isEnabled = false // Disable button

            orderApiService.createOrder(order).enqueue(object : Callback<Order> {
                override fun onResponse(call: Call<Order>, response: Response<Order>) {
                    binding.progressBar.visibility = View.GONE // Hide loading indicator
                    binding.createOrderButton.isEnabled = true // Re-enable button
                    if (response.isSuccessful) {
                        val createdOrder = response.body()
                        Toast.makeText(this@AllProductActivity, "Order created successfully! ID: ${createdOrder?.id}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AllProductActivity, "Failed to create order", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Order>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE // Hide loading indicator
                    binding.createOrderButton.isEnabled = true // Re-enable button
                    Toast.makeText(this@AllProductActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Please select at least one product", Toast.LENGTH_SHORT).show()
        }
    }



    private fun fetchAllProducts() {
        productApiService.getAllProducts().enqueue(object : Callback<List<ProductFetch>> {
            override fun onResponse(call: Call<List<ProductFetch>>, response: Response<List<ProductFetch>>) {
                if (response.isSuccessful && response.body() != null) {
                    val products = response.body()!!
                    setupRecyclerView(products)
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
