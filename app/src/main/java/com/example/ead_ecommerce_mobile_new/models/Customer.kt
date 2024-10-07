package com.example.ead_ecommerce_mobile_new.models

data class Customer(
    val name: String,
    val email: String,
    val address: String?,
    val phone: String?,
    val userType: String = "Customer",
    val isWebUser: Boolean = false,
    val username: String,
    val accountStatus: String = "NotActivate",
    val password: String,
    val avgRating: Double = 0.0
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val userType: String,
    val userId: String,
    val token: String
)

data class UpdateUserResponse(
    val message: String,
    val user: Customer
)
