package com.example.ead_ecommerce_mobile_new.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ead_ecommerce_mobile_new.R
import com.example.ead_ecommerce_mobile_new.databinding.ActivityLoginPageBinding
import com.example.ead_ecommerce_mobile_new.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbtnProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }
}