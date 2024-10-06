package com.example.ead_ecommerce_mobile_new.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityLoginPageBinding
import com.example.ead_ecommerce_mobile_new.models.LoginRequest
import com.example.ead_ecommerce_mobile_new.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPageActivity: AppCompatActivity() {
    private  lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogintosignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.userName.text.toString()
            val password = binding.userPASS.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        RetrofitInstance.customerApi.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        // Successful login
                        val loginResponse = response.body()!!
                        Toast.makeText(this@LoginPageActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                        // Store userId and email in shared preferences or any storage of your choice
                        // Example using SharedPreferences:
                        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("userId", it.userId)
                            putString("email", email)
                            apply()
                        }

                        // Navigate to MainActivity
                        val intent = Intent(this@LoginPageActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Close LoginPageActivity
                    }
                } else {
                    Toast.makeText(this@LoginPageActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginPageActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}