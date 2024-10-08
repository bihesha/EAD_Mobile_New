package com.example.ead_ecommerce_mobile_new.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ead_ecommerce_mobile_new.adapter.OrderAdapter
import com.example.ead_ecommerce_mobile_new.api.Order.OrderApiService
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityAllOrdersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import Order

class AllOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllOrdersBinding
    private lateinit var orderAdapter: OrderAdapter
    private val orderApiService: OrderApiService = RetrofitInstance.orderApiService
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId != null) {
            fetchAllOrders(userId)
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        binding.orderRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchAllOrders(userId: String) {
        orderApiService.getOrdersByUser(userId).enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful && response.body() != null) {
                    val orders = response.body()!!
                    setupRecyclerView(orders)
                } else {
                    Toast.makeText(this@AllOrdersActivity, "Failed to fetch orders", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(this@AllOrdersActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(orders: List<Order>) {
        orderAdapter = OrderAdapter(orders) { selectedOrder ->
            val intent = Intent(this, OrderDetailsActivity::class.java)
            intent.putExtra("orderId", selectedOrder.id)
            startActivity(intent)
        }
        binding.orderRecyclerView.adapter = orderAdapter
    }
}
