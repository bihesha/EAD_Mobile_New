package com.example.ead_ecommerce_mobile_new.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.R
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
                        val loginResponse = response.body()!!

                        // Check if the user is a "Customer"
                        if (loginResponse.userType != "Customer") {
                            Toast.makeText(this@LoginPageActivity, "Only Customers can log in.", Toast.LENGTH_SHORT).show()
                            return
                        }

                        // Check if the account status is "NotActivate"
                        if (loginResponse.accountStatus == "NotActivate") {
                            showNotActivatedMessage()
//                            Toast.makeText(this@LoginPageActivity, "Your cart.io account is not activated. Please contact this number. (071-2145236)", Toast.LENGTH_SHORT).show()
                            return
                        }

                        // Log successful login for Customer
                        Log.d("LoginResponse", "UserId: ${loginResponse.userId}, Email: $email")
                        Toast.makeText(this@LoginPageActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Store userId and email in SharedPreferences
                        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("userId", loginResponse.userId)
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

    private fun showNotActivatedMessage() {
        val failedDialog = Dialog(this)
        failedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        failedDialog.setCancelable(false)
        failedDialog.setContentView(R.layout.login_failed_dialog)

        val buttonFailed : Button = failedDialog.findViewById(R.id.buttonFailed)

        buttonFailed.setOnClickListener {
            failedDialog.dismiss()
        }
        failedDialog.show()
    }
}