package com.example.ead_ecommerce_mobile_new.activities

import Order
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.api.Order.OrderApiService
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityOrderDetailsBinding
import com.example.ead_ecommerce_mobile_new.databinding.ItemOrderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding
    private val orderApiService: OrderApiService = RetrofitInstance.orderApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getStringExtra("orderId")

        // Logger for orderId
        Log.d(TAG, "Received orderId: $orderId") // Log the orderId

        if (orderId != null) {
            fetchOrderDetails(orderId)
        } else {
            Toast.makeText(this, "Order ID not found", Toast.LENGTH_SHORT).show()
        }

        binding.btnBackToOrders.setOnClickListener {
            finish()
        }
    }

//    private fun fetchOrderDetails(orderId: String) {
//        // Use the correct API method for fetching single order details
//        orderApiService.getOrderById(orderId).enqueue(object : Callback<Order> {
//            override fun onResponse(call: Call<Order>, response: Response<Order>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val order = response.body()!!
//                    displayOrderDetails(order)
//                } else {
//                    Toast.makeText(this@OrderDetailsActivity, "Failed to fetch order", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Order>, t: Throwable) {
//                // Log detailed error information
//                Log.e("OrderDetailsActivity", "API call failed", t)
//                Toast.makeText(this@OrderDetailsActivity, "Error fetching order: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

    private fun fetchOrderDetails(orderId: String) {
        // Make sure you're using Call<List<Order>> in the enqueue method
        orderApiService.getOrderById(orderId).enqueue(object : Callback<List<Order>> {

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                    val order = response.body()!![0] // Get the first (and only) order from the list
                    displayOrderDetails(order)
                } else {
                    Toast.makeText(this@OrderDetailsActivity, "Failed to fetch order", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                // Log detailed error information
                Log.e("OrderDetailsActivity", "API call failed", t)
                Toast.makeText(this@OrderDetailsActivity, "Error fetching order: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun displayOrderDetails(order: Order) {
        binding.apply {

            orderNumber.text = order.orderNumber
            orderTotalPrice.text = "Total: ${order.totalPrice}"
            orderStatus.text = order.orderStatus
            deliveryStatus.text = order.deliveryStatus
            orderDate.text = order.orderDate.toString()
            orderCancellationNote.text = order.cancellationNote ?: "N/A"
        }
    }
}
