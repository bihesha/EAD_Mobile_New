package com.example.ead_ecommerce_mobile_new.api.Retrofit

import com.example.ead_ecommerce_mobile_new.api.Customer.CustomerApi

import com.example.ead_ecommerce_mobile_new.api.Rating.RatingApi

import com.example.ead_ecommerce_mobile_new.api.Order.OrderApiService

import com.example.ead_ecommerce_mobile_new.api.Product.ProductApiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL of your backend API
    private const val BASE_URL = "https://e1f8-2402-4000-20c3-d0ae-6807-4304-943d-c831.ngrok-free.app/" // Replace with your backend URL

    // Lazy initialization of Retrofit instance
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)  // Set the base URL for API requests
            .addConverterFactory(GsonConverterFactory.create())  // Use Gson for JSON serialization
            .build()
    }

    // Initialize your API service (e.g., CustomerApi)
    val customerApi: CustomerApi by lazy {
        retrofit.create(CustomerApi::class.java)
    }
    val orderApiService: OrderApiService by lazy {
        retrofit.create(OrderApiService::class.java)
    }
    val productApiService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

}