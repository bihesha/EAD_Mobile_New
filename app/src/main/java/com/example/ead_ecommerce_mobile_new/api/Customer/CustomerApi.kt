package com.example.ead_ecommerce_mobile_new.api.Customer

import com.example.ead_ecommerce_mobile_new.models.Customer
import com.example.ead_ecommerce_mobile_new.models.LoginRequest
import com.example.ead_ecommerce_mobile_new.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CustomerApi {
    @POST("api/User")
    fun registerCustomer(@Body customer: Customer): Call<Customer>

    @POST("api/User/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}