package com.example.travelapp

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
        private const val COL_ID = "ID"
        private const val COL_TITLE = "Title"
        private const val COL_DESCRIPTION = "Description"

        private const val TABLE_LANDMARKS = "Landmarks"
        private const val COL_LAND_TITLE = "Landmark title"
        private const val COL_LAND_DESCRIPTION = "Landmark description"
        private const val COL_LAND_ID = "Landmark ID"
    }
    override fun onCreate(db: SQLiteDatabase?) {

        // Create table Cities
        val createTable = ("CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + "INTEGER PRIMARY KEY,"
                + COL_TITLE + " TEXT,"
                + COL_DESCRIPTION +" TEXT" + ")")

        db?.execSQL(createTable)

        // Create table Landmarks
        val createLandmark = ("CREATE TABLE " + TABLE_LANDMARKS + "("
                + COL_LAND_TITLE + "TEXT,"
                + COL_LAND_DESCRIPTION + " TEXT,"
                + COL_LAND_ID +" INTEGER" + ")")

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(city: City) : Long {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE, city.title)
        cv.put(COL_DESCRIPTION, city.description)
        var result = db.insert(TABLE_NAME, null, cv)

        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

        db.close()
        return result
    }

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
}