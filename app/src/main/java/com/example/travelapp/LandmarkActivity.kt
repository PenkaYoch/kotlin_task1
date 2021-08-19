package com.example.travelapp

import android.app.Dialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_landmark.*
import kotlinx.android.synthetic.main.dialog_landmark.*
import kotlinx.android.synthetic.main.item_landmark.*

//import kotlinx.android.synthetic.main.landmark.*

class LandmarkActivity : AppCompatActivity() {
//    private val landmarkAdapter: LandmarkAdapter by lazy {
//        LandmarkAdapter(mutableListOf(), this)
//    }

    private lateinit var landmarkAdapter: LandmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_landmark)
        landmarkAdapter = LandmarkAdapter(mutableListOf(), this)
        rvLandmarks.adapter = landmarkAdapter
        rvLandmarks.layoutManager = LinearLayoutManager(this)

        val context = this

//        val message = intent.getStringExtra("cityId")

        btnAddLandmark.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialog_landmark)
                dialog.findViewById<Button>(R.id.btn_submit_landmark).setOnClickListener {
                    val landmarkTitle = dialog.findViewById<TextInputLayout>(R.id.landmark_name_input).editText?.text.toString()
                    val landmarkDescription = dialog.findViewById<TextInputLayout>(R.id.landmark_description_input).editText?.text.toString()

                    val landmark = Landmark(landmarkTitle, landmarkDescription)
                    val alertDialogBuilder = AlertDialog.Builder(this)

                    if (!landmarkTitle.isNullOrEmpty() && !landmarkDescription.isNullOrEmpty()) {
                        landmarkAdapter.addLandmark(landmark)

                        landmark_name_input.editText?.text?.clear()
                        landmark_description_input.editText?.text?.clear()
                    } else {
                        alertDialogBuilder.setTitle("Insert valid data")
                        alertDialogBuilder.show()
                    }
                }
                dialog.show()
            }
    }
}