package com.example.travelapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        private const val DATABASE_NAME = "MyDB"

        private const val TABLE_NAME = "Cities"
        private const val COL_ID = "_id"
        private const val COL_TITLE = "Title"
        private const val COL_DESCRIPTION = "Description"

        private const val TABLE_LANDMARKS = "Landmarks"
        private const val COL_LAND_TITLE = "Landmark_Title"
        private const val COL_LAND_DESCRIPTION = "Landmark_Description"
        private const val COL_LAND_ID = "Landmark_ID"
        private const val COL_LAND_CITY_ID = "City_ID"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        // Create table Cities
        val createTable = ("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_TITLE + " TEXT, "
                + COL_DESCRIPTION +" TEXT" + ");")

//        db?.execSQL(createTable)

        // Create table Landmarks
        val createLandmark = ("CREATE TABLE $TABLE_LANDMARKS ( " +
                "$COL_LAND_ID INTEGER PRIMARY KEY, " +
                "$COL_LAND_TITLE TEXT, " +
                "$COL_LAND_DESCRIPTION TEXT, " +
                "$COL_LAND_CITY_ID INTEGER, " +
                "FOREIGN KEY($COL_LAND_CITY_ID) REFERENCES $TABLE_NAME($COL_ID));")

        db?.execSQL(createTable)
        db?.execSQL(createLandmark)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(city: City) : Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_TITLE, city.title)
        cv.put(COL_DESCRIPTION, city.description)
        val result = db.insert(TABLE_NAME, null, cv)

        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

        db.close()
        return result
    }

    fun insertLandmark(landmark: Landmark) : Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_LAND_TITLE, landmark.title)
        cv.put(COL_LAND_DESCRIPTION, landmark.description)
        cv.put(COL_LAND_CITY_ID, landmark.cityId)
        val result = db.insert(TABLE_LANDMARKS, null, cv)

        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

        db.close()
        return result
    }

    @SuppressLint("Range")
    fun viewData(): ArrayList<City> {

        val dataList: ArrayList<City> = ArrayList<City>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var title: String
        var description: String
        var cityId: Int

        if (cursor.moveToFirst()) {
            do {
                cityId = cursor.getInt(cursor.getColumnIndex(COL_ID))
                title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))

                val data = City(cityId = cityId, title = title, description = description)
                dataList.add(data)
            } while (cursor.moveToNext())
        }

        return dataList
    }

    fun deleteData(city: City) : Int {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID, city.cityId)

        val result = db.delete(TABLE_NAME, COL_ID + "=" + city.cityId, null)
        db.close()

        return result
    }

    fun deleteLandmark(landmark: Landmark) : Int {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_LAND_ID, landmark.landmarkId)

        val result = db.delete(TABLE_LANDMARKS, COL_LAND_ID + "=" + landmark.landmarkId, null)
        db.close()

        return result
    }

    @SuppressLint("Range")
    fun viewLandmarks(): ArrayList<Landmark> {

        val dataList: ArrayList<Landmark> = ArrayList<Landmark>()
        val selectQuery = "SELECT * FROM $TABLE_LANDMARKS"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var title: String
        var description: String
        var cityId: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COL_LAND_ID))
                title = cursor.getString(cursor.getColumnIndex(COL_LAND_TITLE))
                description = cursor.getString(cursor.getColumnIndex(COL_LAND_DESCRIPTION))
                cityId = cursor.getInt(cursor.getColumnIndex(COL_LAND_CITY_ID))

                val data = Landmark(id,title = title, description = description, cityId = cityId)
                dataList.add(data)
            } while (cursor.moveToNext())
        }

        return dataList
    }
}