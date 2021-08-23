package com.example.travelapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_landmark.*
import kotlinx.android.synthetic.main.dialog_landmark.*

//import kotlinx.android.synthetic.main.landmark.*

class LandmarkActivity : AppCompatActivity() {
//    private val landmarkAdapter: LandmarkAdapter by lazy {
//        LandmarkAdapter(mutableListOf(), this)
//    }

    private lateinit var landmarkAdapter: LandmarkAdapter
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark)

        this.id = intent.getIntExtra("City id", 0)
        val landmarks = getLandmarks()
        landmarkAdapter = LandmarkAdapter(landmarks.toMutableList(), this)
        rvLandmarks.adapter = landmarkAdapter
        rvLandmarks.layoutManager = LinearLayoutManager(this)

        btnAddLandmark.setOnClickListener {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_landmark)
                dialog.btn_submit_landmark.setOnClickListener {
                    val landmarkTitle = dialog.landmark_name_input.editText?.text.toString()
                    val landmarkDescription = dialog.landmark_description_input.editText?.text.toString()

                    val landmark = Landmark(landmarkAdapter.landmarks.size, landmarkTitle, landmarkDescription, this.id)
                    val alertDialogBuilder = AlertDialog.Builder(this)

                    if (!landmarkTitle.isNullOrEmpty() && !landmarkDescription.isNullOrEmpty()) {
                        landmarkAdapter.addLandmark(landmark)
                        Log.e("tag", "newLandmark $landmark")

                        val dataBaseHandler: DataBaseHandler = DataBaseHandler(this)
                        val status = dataBaseHandler.insertLandmark(landmark)

                        if (status > -1) {
                            Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(applicationContext, "Not saved", Toast.LENGTH_LONG).show()
                        }

                        dialog.dismiss()

//                        landmark_name_input.editText?.text?.clear()
//                        landmark_description_input.editText?.text?.clear()
                    } else {
                        alertDialogBuilder.setTitle("Insert valid data")
                        alertDialogBuilder.show()
                    }
                }
                dialog.show()
            }
    }

    private fun getLandmarks(): List<Landmark> {
        val dataBaseHandler: DataBaseHandler = DataBaseHandler(this)
        val allLandmarks = dataBaseHandler.viewLandmarks()

        return allLandmarks.filter { it.cityId == this.id }
    }
}