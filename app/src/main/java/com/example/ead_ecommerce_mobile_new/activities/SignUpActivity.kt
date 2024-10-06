package com.example.ead_ecommerce_mobile_new.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.databinding.ActivitySignUpBinding

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
    }
}