package com.example.travelapp

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_landmark.view.*
import kotlinx.android.synthetic.main.dialog_landmark.view.*
import org.w3c.dom.Text

class LandmarkAdapter(
    val landmarks: MutableList<Landmark>,
    val contextLandmark: Context
) : RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder>() {

    class LandmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val landmarkName: TextInputLayout = itemView.landmark_name_input
        val landmarkDescription: TextInputLayout = itemView.landmark_description_input
    }

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
        Log.e("tag", "allLandmarks $landmarks")
    }

    override fun onBindViewHolder(holder: LandmarkViewHolder, position: Int) {
        val curLandmark = landmarks[position]

        holder.landmarkName.editText?.setText(curLandmark.title, TextView.BufferType.EDITABLE)
        holder.landmarkDescription.editText?.setText(curLandmark.description, TextView.BufferType.EDITABLE)
    }

    override fun getItemCount(): Int {
        return landmarks.size
    }
}