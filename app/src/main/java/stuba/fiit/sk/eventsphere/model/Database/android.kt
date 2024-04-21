package stuba.fiit.sk.eventsphere.model.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import stuba.fiit.sk.eventsphere.model.User


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = "CREATE TABLE users (" +
                "id INTEGER," +
                "username TEXT," +
                "firstname TEXT," +
                "lastname TEXT," +
                "email TEXT" +
                ")"
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun insertUser(id: Int, username: String, firstname: String, lastname: String, email: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id", id)
        values.put("username", username)
        values.put("firstname", firstname)
        values.put("lastname", lastname)
        values.put("email", email)
        db.insert("users", null, values)
        db.close()
    }

    fun userExists(id: Int): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    @SuppressLint("Range")
    fun getUser(id: Int): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE username = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))
        var user: User? = null
        if (cursor.moveToFirst()) {
            val user_id = cursor.getInt(cursor.getColumnIndex("id"))
            val username = cursor.getString(cursor.getColumnIndex("username"))
            val firstname = cursor.getString(cursor.getColumnIndex("firstname"))
            val lastname = cursor.getString(cursor.getColumnIndex("lastname"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            user = User(
                id = user_id,
                username = username,
                email = email,
                firstname = firstname,
                lastname = lastname,
                profile_image = null
            )
        }
        cursor.close()
        db.close()
        return user
    }

    companion object {
        private const val DATABASE_NAME = "event_sphere.db"
        private const val DATABASE_VERSION = 1
    }
}