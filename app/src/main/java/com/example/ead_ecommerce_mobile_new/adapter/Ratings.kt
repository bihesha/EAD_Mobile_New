package com.example.ead_ecommerce_mobile_new.adapter

import android.content.Intent
import android.util.Log
import com.example.ead_ecommerce_mobile_new.models.Rating
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ead_ecommerce_mobile_new.R
import com.example.ead_ecommerce_mobile_new.activities.UpdateRatingActivity
import com.example.ead_ecommerce_mobile_new.models.Customers

class RatingsAdapter(
    private val ratingsList: List<Rating>,
    private var allUsers: List<Customers>
) : RecyclerView.Adapter<RatingsAdapter.RatingViewHolder>() {

    inner class RatingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.textViewUsername)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val commentTextView: TextView = view.findViewById(R.id.textViewComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rating_activity, parent, false)
        return RatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        val rating = ratingsList[position]
        Log.d("RatingsAdapter", "Fetched ${allUsers.size} users: $allUsers")

        val user = allUsers.find { it.id == rating.vendorId }
        val username = user?.name ?: "Unknown User"

        holder.usernameTextView.text = username
        holder.ratingBar.rating = rating.ratingNo.toFloat()
        holder.commentTextView.text = rating.comment

        Log.d("RatingsAdapter", "User: $username, Rating: ${rating.ratingNo}, Comment: ${rating.comment}")

        // Set OnClickListener for the update button
        holder.itemView.findViewById<Button>(R.id.btn_updatecmt).setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateRatingActivity::class.java)
            intent.putExtra("ratingId", rating.ratingId)
            intent.putExtra("comment", rating.comment)
            intent.putExtra("name", rating.name)  // Pass the username
            intent.putExtra("cusId", rating.cusId)  // Pass the cusId
            intent.putExtra("vendorId", rating.vendorId)  // Pass the vendorId
            intent.putExtra("ratingNo", rating.ratingNo)  // Pass the ratingNo
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ratingsList.size
    }

    fun updateUsers(newUsers: List<Customers>) {
        allUsers = newUsers
        notifyDataSetChanged() // Notify adapter about data change
    }
}
