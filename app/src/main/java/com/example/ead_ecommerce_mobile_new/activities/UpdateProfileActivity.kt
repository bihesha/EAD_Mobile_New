package com.example.ead_ecommerce_mobile_new.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.databinding.ActivityUpdateProfileBinding

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewUpdate.setOnClickListener {
            val intent = Intent(this,UserProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnUpdateBack.setOnClickListener {
            val intent = Intent(this,UserProfileActivity::class.java)
            startActivity(intent)
        }

    }
}