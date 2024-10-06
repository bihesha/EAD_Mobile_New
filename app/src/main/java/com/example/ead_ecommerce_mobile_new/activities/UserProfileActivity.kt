package com.example.ead_ecommerce_mobile_new.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.api.Customer.CustomerApi
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityUserProfileBinding
import com.example.ead_ecommerce_mobile_new.models.Customer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val customerApi: CustomerApi = RetrofitInstance.customerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId != null) {
            fetchUserDetails(userId)
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        binding.btnviewUpdate.setOnClickListener {
            val intent = Intent(this,UpdateProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btndelete.setOnClickListener {
            val intent = Intent(this,LoginPageActivity::class.java)
            startActivity(intent)
        }

        binding.btnBackViewProfile.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUserDetails(userId: String) {
        customerApi.getSingleUser(userId).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    binding.userNameprofile.text = user.name
                    binding.userEmailprofile.text = user.email
                    binding.userADDprofile.text = user.address ?: "Not Provided"
                    binding.userPhoneprofile.text = user.phone ?: "Not Provided"
                    binding.userUSERNAMEprofile.text = user.username
                    binding.userPASSprofile.text = user.password
                    // Set other user details as needed
                } else {
                    Toast.makeText(this@UserProfileActivity, "Failed to fetch user details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}