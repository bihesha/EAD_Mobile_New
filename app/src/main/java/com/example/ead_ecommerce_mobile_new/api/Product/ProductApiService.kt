package com.example.ead_ecommerce_mobile_new.api.Product

import Product
import com.example.ead_ecommerce_mobile_new.models.ProductFetch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("/api/Product/getAllProducts")
    fun getAllProducts(): Call<List<ProductFetch>>

    @GET("/api/Product/getProductById/{id}")
    fun getProductById(@Path("id") id: String): Call<Product>  // Single product, not a list
}
