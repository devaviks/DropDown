package com.example.dropdown

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SALUTATION_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + COUNTRY_COL + " TEXT,"
                + STATE_COL + " TEXT,"
                + GENDER_COL + " TEXT)")
        db.execSQL(query)
    }

    fun addNewCourse(course: Course) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(SALUTATION_COL, course.salutation)
        values.put(NAME_COL, course.name)
        values.put(COUNTRY_COL, course.country)
        values.put(STATE_COL, course.state)
        values.put(GENDER_COL, course.gender)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "detailsdb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "mydetails"
        private const val ID_COL = "id"
        private const val SALUTATION_COL = "salutation"
        private const val NAME_COL = "name"
        private const val COUNTRY_COL = "country"
        private const val STATE_COL = "state"
        private const val GENDER_COL = "gender"
    }


    @SuppressLint("Range")
    fun getAllCourses(): List<Course> {
        val courses = mutableListOf<Course>()
        val db = this.readableDatabase

        val columns = arrayOf(ID_COL, SALUTATION_COL, NAME_COL, COUNTRY_COL, STATE_COL, GENDER_COL)
        val cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)

        cursor?.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndex(ID_COL))
                val salutation = c.getString(c.getColumnIndex(SALUTATION_COL))
                val name = c.getString(c.getColumnIndex(NAME_COL))
                val country = c.getString(c.getColumnIndex(COUNTRY_COL))
                val state = c.getString(c.getColumnIndex(STATE_COL))
                val gender = c.getString(c.getColumnIndex(GENDER_COL))

                val course = Course(salutation, name, country, state, gender)
                courses.add(course)
            }
        }

        cursor?.close()
        db.close()

        return courses
    }
}
