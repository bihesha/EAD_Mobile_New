package com.example.ead_ecommerce_mobile_new.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}