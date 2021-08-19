package com.example.travelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_city.*
import kotlinx.android.synthetic.main.item_city.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityAdapter = CityAdapter(mutableListOf(), this)

        rvCities.adapter = cityAdapter
        rvCities.layoutManager = LinearLayoutManager(this)
//        cityAdapter.onAddLandmark = { landmark ->
//            //todo intent list
//
//        }

        val context = this

        var cityCounter: Int = 555555

        fun getItemsList(): ArrayList<City> {
            val dataBaseHandler: DataBaseHandler = DataBaseHandler(this)

            return dataBaseHandler.viewData()
        }

        fun setupListOfDataIntoRecyclerView() {
            if (getItemsList().size > 0) {
                rvCities.visibility = View.VISIBLE
                rvCities.layoutManager = LinearLayoutManager(this)
                rvCities.adapter = cityAdapter
            }
        }

        btnAddCity.setOnClickListener {
            val cityTitle = etCityName.text.toString()
            val cityDescription = etCityDescription.text.toString()
            val city = City(cityAdapter.cities.size, cityTitle, cityDescription)
            val alertDialogBuilder = AlertDialog.Builder(this)
            cityCounter++
            val dataBaseHandler: DataBaseHandler = DataBaseHandler(this)

            if (cityTitle.isNotEmpty() && !cityAdapter.cities.contains(city) && cityDescription.isNotEmpty()) {
                cityAdapter.addCity(city)

                Log.e("tag", "newCity ${cityAdapter.cities}")

                val status = dataBaseHandler.insertData(City(cityCounter, cityTitle, cityDescription))

                if (status > -1) {
                    Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Not saved", Toast.LENGTH_LONG).show()
                    Log.e("tag", "failedCity ${cityAdapter.cities}")
                }

                var db = DataBaseHandler(context)
                db.insertData(city)

                setupListOfDataIntoRecyclerView()

                etCityName.text.clear()
                etCityDescription.text.clear()
            } else if(cityAdapter.cities.contains(city)) {
                alertDialogBuilder.setTitle("This city already exists")
                alertDialogBuilder.show()
                etCityName.text.clear()
                etCityDescription.text.clear()
            } else {
                alertDialogBuilder.setTitle("Enter valid name and description")
                alertDialogBuilder.show()
            }
        }
    }
}