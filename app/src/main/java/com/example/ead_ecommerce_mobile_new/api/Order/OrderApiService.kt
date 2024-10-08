package com.example.ead_ecommerce_mobile_new.api.Order

import Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApiService {
    @GET("/api/Order/getAllOrders")
    fun getAllOrders(): Call<List<Order>>

    @GET("/api/Order/getOrdersByUser/{userId}")
    fun getOrdersByUser(@Path("userId") userId: String): Call<List<Order>>

    @GET("/api/Order/get/{id}")
    fun getOrderById(@Path("id") id: String): Call<List<Order>>  // New method for single order details

    @PUT("/api/orders/{orderId}/cancel")
    fun cancelOrder(
        @Path("orderId") orderId: String,
        @Body cancellationNote: String
    ): Call<Order>
}

