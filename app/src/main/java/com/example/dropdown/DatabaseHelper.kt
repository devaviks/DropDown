package com.example.dropdown

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "details.db"
        private const val DB_VERSION = 1
        const val TABLE_NAME_USER = "tbl_user"
        const val TABLE_NAME_GROUP = "tbl_group"
        const val TABLE_NAME_GROUP_USERS = "tbl_group_users"

        const val PK_ID = "_id"
        private const val SALUTATION_COL = "salutation"
        private const val NAME_COL = "name"
        private const val COUNTRY_COL = "country"
        private const val STATE_COL = "state"
        private const val GENDER_COL = "gender"
        private const val IMAGE_COL = "imageBase64"


        const val COLUMN_GROUP_NAME = "group_name"


        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_GROUP_ID = "group_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + " ("
                + PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SALUTATION_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + COUNTRY_COL + " TEXT,"
                + STATE_COL + " TEXT,"
                + GENDER_COL + " TEXT,"
                + IMAGE_COL + " TEXT)")

        val createGroupTableSQL = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_GROUP + " ("
                + PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_GROUP_NAME + " TEXT)")


        val createGroupUsersTableSQL = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_GROUP_USERS + " ("
                + PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ID + " TEXT,"
                + COLUMN_GROUP_ID + " TEXT)")

        db.execSQL(query)
        db.execSQL(createGroupTableSQL)
        db.execSQL(createGroupUsersTableSQL)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_USER")
        onCreate(db)
    }

    fun addNewCourse(course: Course) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(SALUTATION_COL, course.salutation)
        values.put(NAME_COL, course.name)
        values.put(COUNTRY_COL, course.country)
        values.put(STATE_COL, course.state)
        values.put(GENDER_COL, course.gender)
        values.put(IMAGE_COL, course.imageBase64)
        db.insert(TABLE_NAME_USER, null, values)
        db.close()
    }

    fun addGroup(groupName: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_GROUP_NAME, groupName)

        try {
            val insertedId = db.insert(TABLE_NAME_GROUP, null, values)
            db.close()
            return insertedId
        } catch (e: Exception) {
            db.close()
            throw e
        }
    }

    fun addGroupUser(userId: Long, groupId: Long) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_ID, userId)
        values.put(COLUMN_GROUP_ID, groupId)
        db.insert(TABLE_NAME_GROUP_USERS, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getGroupData(context: Context, groupId: Int): String {
        val groupDatabaseHelper = DBHandler(context)
        val db = groupDatabaseHelper.readableDatabase
        val columns = arrayOf(COLUMN_GROUP_NAME)

        val selection = "$PK_ID = ?"
        val selectionArgs = arrayOf(groupId.toString())

        val cursor = db.query(
            TABLE_NAME_GROUP,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var groupData = ""

        if (cursor.moveToFirst()) {
            val groupName =
                cursor.getString(cursor.getColumnIndex(COLUMN_GROUP_NAME))
            groupData = "Group Name: $groupName"
        }

        cursor.close()
        return groupData
    }


    @SuppressLint("Range")
    fun getAllCourses(pageNumber: Int, pageSize: Int): List<Course> {
        val courses = mutableListOf<Course>()
        val db = this.readableDatabase

        val offset = pageNumber * pageSize

        val columns = arrayOf(PK_ID, SALUTATION_COL, NAME_COL, COUNTRY_COL, STATE_COL, GENDER_COL, IMAGE_COL)
        val orderBy = "$PK_ID ASC"
        val limit = "$offset, $pageSize"

        val cursor = db.query(TABLE_NAME_USER, columns, null, null, null, null, orderBy, limit)

        cursor?.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndex(PK_ID))
                val salutation = c.getString(c.getColumnIndex(SALUTATION_COL))
                val name = c.getString(c.getColumnIndex(NAME_COL))
                val country = c.getString(c.getColumnIndex(COUNTRY_COL))
                val state = c.getString(c.getColumnIndex(STATE_COL))
                val gender = c.getString(c.getColumnIndex(GENDER_COL))
                val imageBase64 = c.getString(c.getColumnIndex(IMAGE_COL))

                val course = Course(id, salutation, name, country, state, gender, imageBase64)
                courses.add(course)
            }
        }

        cursor?.close()
        db.close()

        return courses
    }


    @SuppressLint("Range")
    fun getGroupUserDetails(): List<GroupUserDetails> {
        val detailsList = mutableListOf<GroupUserDetails>()

        val query = """
        SELECT
            g.group_name,
            u.salutation,
            u.name,
            u.country,
            u.state,
            u.gender
        FROM
            $TABLE_NAME_GROUP_USERS gu
        JOIN
            $TABLE_NAME_GROUP g ON gu.group_id = g.$PK_ID
        JOIN
            $TABLE_NAME_USER u ON gu.user_id = u.$PK_ID
    """

        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        cursor.use { c ->
            while (c.moveToNext()) {
                val groupName = c.getString(c.getColumnIndex("group_name"))
                val salutation = c.getString(c.getColumnIndex("salutation"))
                val name = c.getString(c.getColumnIndex("name"))
                val country = c.getString(c.getColumnIndex("country"))
                val state = c.getString(c.getColumnIndex("state"))
                val gender = c.getString(c.getColumnIndex("gender"))

                val groupUserDetails = GroupUserDetails(
                    groupName,
                    salutation,
                    name,
                    country,
                    state,
                    gender
                )

                detailsList.add(groupUserDetails)
            }
        }

        cursor.close()
        db.close()

        return detailsList
    }
}







