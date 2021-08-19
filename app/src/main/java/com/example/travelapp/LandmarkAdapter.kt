package com.example.travelapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_landmark.view.*

class LandmarkAdapter(
    val landmarks: MutableList<Landmark>,
    val contextLandmark: Context
) : RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder>() {

    class LandmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkViewHolder {
        return LandmarkViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.dialog_landmark,
                parent,
                false
            )
        )
    }

    fun addLandmark(landmark: Landmark) {
        landmarks.add(landmark)
        notifyItemInserted(landmarks.size - 1)
    }

    override fun onBindViewHolder(holder: LandmarkViewHolder, position: Int) {
        val curLandmark = landmarks[position]

        holder.itemView.apply {
            item_landmark_name.text = curLandmark.title
            item_landmark_description.text = curLandmark.description
        }
    }

    override fun getItemCount(): Int {
        return landmarks.size
    }
}