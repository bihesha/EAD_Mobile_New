package com.example.ead_ecommerce_mobile_new.api.Customer

import com.example.ead_ecommerce_mobile_new.models.Customer
import com.example.ead_ecommerce_mobile_new.models.Customers
import com.example.ead_ecommerce_mobile_new.models.LoginRequest
import com.example.ead_ecommerce_mobile_new.models.LoginResponse
import com.example.ead_ecommerce_mobile_new.models.UpdateUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApi {
    @POST("api/User")
    fun registerCustomer(@Body customer: Customer): Call<Customer>

    @POST("api/User/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("api/User/getSingleUser/{id}")
    fun getSingleUser(@Path("id") userId: String): Call<Customer>

    @PUT("api/User/updateUser/{id}")
    fun updateCustomer(@Path("id") userId: String, @Body customer: Customer): Call<UpdateUserResponse>

    @DELETE("api/User/deleteUser/{id}")
    fun deleteUser(@Path("id") userId: String): Call<Void>

    @PUT("api/User/updateAccountStatus/{id}")
    fun updateAccountStatus(@Path("id") userId: String, @Body accountStatus: String): Call<Void>

    @GET("api/User/GetAllUsers")
    fun getAllUsers(): Call<List<Customers>>

}