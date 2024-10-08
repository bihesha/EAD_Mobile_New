package com.example.ead_ecommerce_mobile_new.activities

import android.annotation.SuppressLint
import android.content.Intent
import com.example.ead_ecommerce_mobile_new.models.Rating
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ead_ecommerce_mobile_new.R
import com.example.ead_ecommerce_mobile_new.adapter.RatingsAdapter
import com.example.ead_ecommerce_mobile_new.api.Rating.RatingApi
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityRatingBinding
import com.example.ead_ecommerce_mobile_new.models.Customer
import com.example.ead_ecommerce_mobile_new.models.Customers
import com.example.ead_ecommerce_mobile_new.models.RatingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var ratingsAdapter: RatingsAdapter
    private lateinit var userId: String
    private var allUsers: List<Customers> = emptyList()
    private var ratings: List<Rating> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewRatings)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch userId from preferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null) ?: ""

        if (userId.isNotEmpty()) {
            fetchRatings(userId)  // Fetch ratings based on cusId (same as userId)
            fetchAllUsers() // Fetch all users to map vendorId to user name
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchRatings(userId: String) {
        Log.d("RatingsActivity", "Fetching ratings for customer ID: $userId")
        // Use Retrofit to make a network request
        val call = RetrofitInstance.retrofit.create(RatingApi::class.java).getRatingsByCusId(userId)

        call.enqueue(object : Callback<RatingResponse> {
            override fun onResponse(call: Call<RatingResponse>, response: Response<RatingResponse>) {
                if (response.isSuccessful) {
                    val ratings = response.body()?.ratings ?: emptyList()
                    Log.d("RatingsActivity", "Fetched ${ratings.size} ratings: $ratings")
                    setupRecyclerView(ratings) // Moved here to ensure ratings are set before users
                    fetchAllUsers()
                } else {
                    Log.e("RatingsActivity", "Failed to fetch ratings: ${response.errorBody()?.string()}")
                    Toast.makeText(this@RatingsActivity, "Failed to fetch ratings", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
                Log.e("RatingsActivity", "Error fetching ratings: ${t.message}", t)
                Toast.makeText(this@RatingsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Fetch all users to map vendorId to username
    private fun fetchAllUsers() {
        val call = RetrofitInstance.customerApi.getAllUsers()
        call.enqueue(object : Callback<List<Customers>> {
            override fun onResponse(call: Call<List<Customers>>, response: Response<List<Customers>>) {
                if (response.isSuccessful) {
                    allUsers = response.body() ?: emptyList()
                    Log.d("RatingsActivity", "Fetched ${allUsers.size} users: $allUsers")
                    // Log all user IDs for better tracking
                    allUsers.forEach { Log.d("RatingsActivity", "User ID: ${it.id}, Name: ${it.name}") }
                    ratingsAdapter.updateUsers(allUsers)
                } else {
                    Log.e("RatingsActivity", "Failed to fetch users: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Customers>>, t: Throwable) {
                Log.e("RatingsActivity", "Error fetching users: ${t.message}", t)
            }
        })
    }

    private fun setupRecyclerView(ratings: List<Rating>) {
        ratingsAdapter = RatingsAdapter(ratings, allUsers)
        recyclerView.adapter = ratingsAdapter
    }
}
