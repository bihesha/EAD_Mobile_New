package com.example.ead_ecommerce_mobile_new.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.api.Rating.RatingApi
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityCreateRatingBinding
import com.example.ead_ecommerce_mobile_new.models.AddRating
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewRating : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRatingBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        // Retrieve the productVendor passed from OrderDetailsActivity
        val productVendor = intent.getStringExtra("productVendor")

        // Now you can use the productVendor variable in this activity
        Log.d("AddNewRating", "Received productVendor: $productVendor")

        binding.btnUpdateBack.setOnClickListener {
            val intent = Intent(this,AllOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.btnViewUpdate.setOnClickListener {
            val addRating = AddRating(
                name = binding.userNamerating.text.toString(),
                cusId = userId.toString(),
                vendorId = productVendor.toString(),
                comment = binding.userCmtrating.text.toString(),
                ratingNo = binding.userAddrating.text.toString().toInt()
            )
            createRating(addRating)
        }
    }

    private fun createRating(addRating: AddRating) {
        // Get the Rating API from the Retrofit instance
        val ratingApi = RetrofitInstance.retrofit.create(RatingApi::class.java)

        // Make the API call to create a new rating
        val call: Call<AddRating> = ratingApi.createRating(addRating)

        // Execute the API call asynchronously
        call.enqueue(object : Callback<AddRating> {
            override fun onResponse(call: Call<AddRating>, response: Response<AddRating>) {
                if (response.isSuccessful) {
                    // Rating successfully created
                    Toast.makeText(this@AddNewRating, "Rating added successfully", Toast.LENGTH_SHORT).show()

                    // Redirect or perform any action as needed, e.g., navigating to another activity
                    val intent = Intent(this@AddNewRating, RatingsActivity::class.java)
                    startActivity(intent)
                } else {
                    // Handle failure, such as validation errors
                    Toast.makeText(this@AddNewRating, "Failed to add rating: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddRating>, t: Throwable) {
                // Handle any network or unexpected errors
                Toast.makeText(this@AddNewRating, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}