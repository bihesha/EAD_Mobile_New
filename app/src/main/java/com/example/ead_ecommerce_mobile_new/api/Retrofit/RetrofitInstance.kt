package com.example.ead_ecommerce_mobile_new.api.Retrofit

import com.example.ead_ecommerce_mobile_new.api.Customer.CustomerApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL of your backend API
    private const val BASE_URL = "https://265e-175-157-29-101.ngrok-free.app/" // Replace with your backend URL

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
}