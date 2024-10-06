package com.example.ead_ecommerce_mobile_new.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivitySignUpBinding
import com.example.ead_ecommerce_mobile_new.models.Customer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signuptoLogin.setOnClickListener {
            val intent = Intent(this,LoginPageActivity::class.java)
            startActivity(intent)
        }

        binding.btnSubmit.setOnClickListener {
            val customer = Customer(
                name = binding.userName.text.toString(),
                email = binding.userEmail.text.toString(),
                address = binding.userADD.text.toString(),
                phone = binding.userPhone.text.toString(),
                username = binding.userUSERNAME.text.toString(),
                password = binding.userPASS.text.toString()
            )

            registerCustomer(customer)
        }
    }

    private fun registerCustomer(customer: Customer) {
        val call = RetrofitInstance.customerApi.registerCustomer(customer)
        call.enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                    // Handle successful registration (e.g., navigate to login screen)
                    val intent = Intent(this@SignUpActivity,LoginPageActivity::class.java)
                    startActivity(intent)
                } else {
                    // If registration failed, log the error body
                    val errorBody = response.errorBody()?.string()  // Extract the error message
                    Toast.makeText(this@SignUpActivity, "Registration Failed: $errorBody", Toast.LENGTH_LONG).show()

                    // Print to the log for debugging
                    android.util.Log.e("SignUpActivity", "Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                // If the request completely failed (e.g., no internet, server down)
                Toast.makeText(this@SignUpActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()

                // Print the throwable message to the log for debugging
                android.util.Log.e("SignUpActivity", "Network error: ${t.message}")
            }
        })
    }
}