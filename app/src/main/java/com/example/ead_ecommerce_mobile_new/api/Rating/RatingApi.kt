package com.example.ead_ecommerce_mobile_new.api.Rating

import com.example.ead_ecommerce_mobile_new.models.Rating
import com.example.ead_ecommerce_mobile_new.models.RatingResponse
import com.example.ead_ecommerce_mobile_new.models.UpdateRatingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RatingApi {

    @GET("api/Rating/GetRatingsByCusId/{cusId}")
    fun getRatingsByCusId(@Path("cusId") userId: String): Call<RatingResponse>

    @PUT("api/Rating/UpdateRatingById/{id}")
    fun updateRatingById(@Path("id") ratingId: String, @Body rating: Rating): Call<UpdateRatingResponse>

}