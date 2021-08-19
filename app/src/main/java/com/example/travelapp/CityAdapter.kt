package com.example.travelapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.dialog_landmark.view.*
import kotlinx.android.synthetic.main.item_city.*
import kotlinx.android.synthetic.main.item_city.view.*

//import kotlinx.android.synthetic.main.landmark.*

class CityAdapter(
    val cities: MutableList<City>,
    private val contextCity: Context
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

//    var onAddLandmark: (landmark: Landmark) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_city,
                parent,
                false
            )
        )
    }

    fun addCity(city: City) {
        cities.add(city)
        city.cityId = cities.size + 1
        Log.e("tag", "newCity $cities")
        notifyItemInserted(cities.size - 1)
//        notifyDataSetChanged()
    }

    private fun deleteCity(city: City) {
        cities.remove(city)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val curCity = cities[position]

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    contextCity,
                    R.color.teal_200
                )
            )
        }

        holder.itemView.apply {
            individualCity.text = curCity.title
            individualDescription.text = curCity.description

            individualCity.setOnLongClickListener {
                deleteCity(cities[position])
                Toast.makeText(contextCity, "Deleted successful", Toast.LENGTH_SHORT).show()

                val databaseHandler: DataBaseHandler = DataBaseHandler(context)
                val status = databaseHandler.deleteData(City(curCity.cityId, "", ""))

                return@setOnLongClickListener true
            }

            btnCityLandmarks.setOnClickListener {
                val intent = Intent(context, LandmarkActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}