package com.example.ead_ecommerce_mobile_new.models

data class Rating(
    val ratingId: String,
    val name: String,
    val cusId: String,
    val vendorId: String,
    val comment: String,
    val ratingNo: Int
)

data class RatingResponse(
    val message: String,
    val ratings: List<Rating>
)

data class UpdateRatingResponse(
    val message: String,
    val rating: Rating
)