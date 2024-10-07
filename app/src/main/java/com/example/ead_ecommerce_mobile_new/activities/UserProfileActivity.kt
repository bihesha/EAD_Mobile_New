package com.example.ead_ecommerce_mobile_new.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            confirmDeletion(userId)
        }

        binding.btndeactivate.setOnClickListener {
            confirmDeactivation(userId)
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

    private fun confirmDeletion(userId: String?) {
        // Show a confirmation dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete your profile?")
            .setPositiveButton("Yes") { _, _ ->
                userId?.let { deleteUser(it) } // Proceed with deletion if userId is not null
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun confirmDeactivation(userId: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Deactivation")
            .setMessage("Are you sure you want to deactivate your account?")
            .setPositiveButton("Yes") { _, _ -> userId?.let { deactivateUser(it) } }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deactivateUser(userId: String) {
        // Call your updateAccountStatus API here to set account status to "NotActivate"
        customerApi.updateAccountStatus(userId, "NotActivate").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserProfileActivity, "Account deactivated successfully", Toast.LENGTH_SHORT).show()
                    // Clear shared preferences and redirect to login page
                    sharedPreferences.edit().clear().apply()
                    startActivity(Intent(this@UserProfileActivity, LoginPageActivity::class.java))
                    finish() // Close the current activity
                } else {
                    Toast.makeText(this@UserProfileActivity, "Failed to deactivate account", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUser(userId: String) {
        customerApi.deleteUser(userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserProfileActivity, "User deleted successfully", Toast.LENGTH_SHORT).show()
                    // Clear shared preferences and navigate to login page or main activity
                    sharedPreferences.edit().clear().apply()
                    startActivity(Intent(this@UserProfileActivity, LoginPageActivity::class.java))
                    finish() // Close the current activity
                } else {
                    Toast.makeText(this@UserProfileActivity, "Failed to delete user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}