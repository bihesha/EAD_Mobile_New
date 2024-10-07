package com.example.ead_ecommerce_mobile_new.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.api.Customer.CustomerApi
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityUpdateProfileBinding
import com.example.ead_ecommerce_mobile_new.models.Customer
import com.example.ead_ecommerce_mobile_new.models.UpdateUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val customerApi: CustomerApi = RetrofitInstance.customerApi
    private lateinit var currentUser: Customer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId != null) {
            fetchUserDetails(userId) // Fetch user details to prepopulate the form
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        binding.btnViewUpdate.setOnClickListener {
            if (userId != null) {
                updateUserDetails(userId) // Update user details on button click
            } else {
                Toast.makeText(this, "User ID is missing. Unable to update", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdateBack.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUserDetails(userId: String) {
        customerApi.getSingleUser(userId).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful && response.body() != null) {
                    currentUser = response.body()!!
                    binding.userNameupdate.setText(currentUser.name)
                    binding.userEmailupdate.setText(currentUser.email)
                    binding.userADDupdate.setText(currentUser.address)
                    binding.userTELEupdate.setText(currentUser.phone)
                    binding.userUSERNAMEupdate.setText(currentUser.username)
                    binding.userPASSupdate.setText(currentUser.password)
                } else {
                    Toast.makeText(this@UpdateProfileActivity, "Failed to fetch user details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Log.e("UpdateProfileActivity", "Error fetching user details", t)
            }
        })
    }


    private fun updateUserDetails(userId: String) {
        // Create a Customer object with updated details
        val updatedCustomer = Customer(
            name = binding.userNameupdate.text.toString(),
            email = binding.userEmailupdate.text.toString(),
            address = binding.userADDupdate.text.toString(),
            phone = binding.userTELEupdate.text.toString(),
            username = binding.userUSERNAMEupdate.text.toString(),
            password = binding.userPASSupdate.text.toString(),
            // Keep other fields as they were; handle them according to your needs
            accountStatus = currentUser.accountStatus, // Make sure to handle password correctly
            isWebUser = currentUser.isWebUser,
            userType = currentUser.userType,
            avgRating = currentUser.avgRating
        )

        customerApi.updateCustomer(userId, updatedCustomer).enqueue(object : Callback<UpdateUserResponse> {
            override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                // Log the response object for debugging (without string())
                // Log the entire response
                Log.d("Response Code", response.code().toString())
                Log.d("Response Message", response.message())
                Log.d("Raw Response", response.raw().toString())

                // Check if the body is not null and log it
                if (response.body() != null) {
                    Log.d("Fetch Response", response.body().toString())
                } else {
                    Log.e("UpdateProfileActivity", "Response body is null")
                }

                if (response.isSuccessful) {
                    Toast.makeText(this@UpdateProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    // Optionally navigate back to profile or user list
                    // Navigate back to UserProfileActivity
                    val intent = Intent(this@UpdateProfileActivity, UserProfileActivity::class.java)
                    startActivity(intent)
                    finish() // Close this activity
                } else {
                    Log.e("UpdateProfileActivity", "Response Code: ${response.code()}, Message: ${response.message()}")
                    Toast.makeText(this@UpdateProfileActivity, "Failed to update profile: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                Log.e("UpdateProfileActivity", "Error updating profile", t)
            }
        })
    }
}
