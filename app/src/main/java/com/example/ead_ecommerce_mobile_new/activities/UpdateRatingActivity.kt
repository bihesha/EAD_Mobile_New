package com.example.ead_ecommerce_mobile_new.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_mobile_new.R
import com.example.ead_ecommerce_mobile_new.api.Rating.RatingApi
import com.example.ead_ecommerce_mobile_new.api.Retrofit.RetrofitInstance
import com.example.ead_ecommerce_mobile_new.databinding.ActivityUpdateRatingBinding
import com.example.ead_ecommerce_mobile_new.models.Rating
import com.example.ead_ecommerce_mobile_new.models.UpdateRatingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateRatingActivity : AppCompatActivity() {

    private lateinit var editTextComment: EditText
    private lateinit var submitButton: Button
    private lateinit var backButton: ImageView
    private lateinit var userId: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityUpdateRatingBinding
    private lateinit var currentRating: Rating

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_rating)

        binding = ActivityUpdateRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the ratingId and comment from the Intent
        val ratingId = intent.getStringExtra("ratingId")
        val comment = intent.getStringExtra("comment")
        val name = intent.getStringExtra("name")
        val cusId = intent.getStringExtra("cusId")
        val vendorId = intent.getStringExtra("vendorId")
        val ratingNo = intent.getIntExtra("ratingNo", 0)

        // Initialize currentRating with these values
        currentRating = Rating(
            ratingId = ratingId ?: "",
            name = name ?: "",
            cusId = cusId ?: "",
            vendorId = vendorId ?: "",
            comment = comment ?: "",
            ratingNo = ratingNo
        )


        // Set the comment in the EditText
        if (comment != null) {
            binding.userCmtupdate.setText(comment)
        }

        binding.btnUpdateBack.setOnClickListener {
            val intent = Intent(this,RatingActivity::class.java)
            startActivity(intent)
        }

        // Handle update logic using ratingId (e.g., API call to update comment)
        binding.btnViewUpdate.setOnClickListener {
            val updatedComment = binding.userCmtupdate.text.toString()
            if (updatedComment.isNotEmpty()) {
                updateRatingComment(ratingId)
            } else {
                Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateRatingComment(ratingId: String?) {
        if (ratingId == null) {
            Toast.makeText(this, "Rating ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the Rating object with the updated comment
        val updatedRating = Rating(
            ratingId = ratingId,
            name = currentRating.name,  // Use currentRating details
            cusId = currentRating.cusId,
            vendorId = currentRating.vendorId,
            comment = binding.userCmtupdate.text.toString(),
            ratingNo = currentRating.ratingNo
        )

        // Make an API call to update the rating
        val call = RetrofitInstance.retrofit.create(RatingApi::class.java)
            .updateRatingById(ratingId, updatedRating)

        call.enqueue(object : Callback<UpdateRatingResponse> {
            override fun onResponse(
                call: Call<UpdateRatingResponse>,
                response: Response<UpdateRatingResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UpdateRatingActivity, "Rating updated successfully", Toast.LENGTH_SHORT).show()

                    // Go back to the ratings list and refresh it
                    val intent = Intent(this@UpdateRatingActivity, RatingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()  // Close this activity
                } else {
                    Toast.makeText(this@UpdateRatingActivity, "Failed to update rating", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateRatingResponse>, t: Throwable) {
                Toast.makeText(this@UpdateRatingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}