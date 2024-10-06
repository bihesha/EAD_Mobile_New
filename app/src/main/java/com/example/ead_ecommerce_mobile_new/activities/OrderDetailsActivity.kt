package com.example.ead_ecommerce_mobile_new.activities

import Order
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.api.Order.OrderApiService
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityOrderDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val orderApiService: OrderApiService = RetrofitInstance.orderApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId != null) {
            Log.d("UserId", "UserId: $userId")
            fetchOrderDetails(userId)
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        // Setup button listeners for back or other functionalities
        binding.btnBackToOrders.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchOrderDetails(userId: String) {
        // Assuming you fetch orders by user ID
        orderApiService.getOrdersByUser(userId).enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful && response.body() != null) {
                    val orders = response.body()!!
                    if (orders.isNotEmpty()) {
                        val order = orders[0] // Assuming you display the first order
                        displayOrderDetails(order)
                    } else {
                        Toast.makeText(this@OrderDetailsActivity, "No orders found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@OrderDetailsActivity, "Failed to fetch orders", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(this@OrderDetailsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayOrderDetails(order: Order) {
        // Bind the order details to the views
        binding.orderId.text = order.id
        binding.orderNumber.text = order.orderNumber
        binding.orderTotalPrice.text = "Total: ${order.totalPrice}"
        binding.orderStatus.text = order.orderStatus
        binding.deliveryStatus.text = order.deliveryStatus
        binding.orderDate.text = order.orderDate.toString()
        binding.orderCancellationNote.text = order.cancellationNote ?: "N/A"
        // Additional product details display if needed
        if (order.products.isNotEmpty()) {
            // Display product details, for now logging the first product
            val product = order.products[0]
            Log.d("ProductDetails", "Product Name: ${product.productName}")
            // You can also bind product details to UI here
        }
    }
}
