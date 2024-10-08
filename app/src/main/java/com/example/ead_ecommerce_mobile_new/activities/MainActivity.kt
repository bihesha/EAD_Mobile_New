package com.example.ead_ecommerce_mobile_new.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ead_ecommerce_mobile_new.R
import com.example.ead_ecommerce_mobile_new.databinding.ActivityLoginPageBinding
import com.example.ead_ecommerce_mobile_new.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        binding.imgbtnProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }


        binding.imgbtnRating.setOnClickListener {
            val intent = Intent(this,RatingsActivity::class.java)
            startActivity(intent)
        }


        binding.imgbtnOrder.setOnClickListener {
            val intent = Intent(this, AllOrdersActivity::class.java)
            startActivity(intent)


        binding.logOutCustomer.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Log Out")
            builder.setMessage("Are you sure you want to log out?")


                builder.setPositiveButton("Yes") { dialog, which ->
                    // Clear user session data from SharedPreferences
                    sharedPreferences.edit().clear().apply()

                    Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginPageActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                builder.setNegativeButton("No") { dialog, which ->
                    // Do nothing
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()

            }

            binding.imgbtnProduct.setOnClickListener {
                val intent = Intent(this, AllProductActivity::class.java)
                startActivity(intent)
            }

        }
    }
}