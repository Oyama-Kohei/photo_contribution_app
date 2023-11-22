package com.example.android_trip_2023_app.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android_trip_2023_app.model.TeamDatabaseModel
import com.example.android_trip_2023_app.model.UserDatabaseModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Database.db"
        private const val DATABASE_VERSION = 1

        // テーブル名とカラム名を定義
        private const val TABLE_USER_DATA = "UserData"
        private const val COLUMN_USER_ID = "user_id"

        private const val TABLE_TEAM_DATA = "TeamData"
        private const val COLUMN_TEAM_ID = "team_id"
        private const val COLUMN_TEAM_NAME = "team_name"
        private const val COLUMN_TEAM_COLOR = "team_color"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // ユーザーデータテーブルの作成クエリ
        val createUserDataTableQuery =
            "CREATE TABLE $TABLE_USER_DATA ($COLUMN_USER_ID TEXT PRIMARY KEY, $COLUMN_TEAM_ID TEXT, $COLUMN_TEAM_NAME TEXT, $COLUMN_TEAM_COLOR TEXT)"
        db.execSQL(createUserDataTableQuery)

        // チームデータテーブルの作成クエリ
        val createTeamDataTableQuery =
            "CREATE TABLE $TABLE_TEAM_DATA ($COLUMN_TEAM_ID TEXT PRIMARY KEY, $COLUMN_TEAM_NAME TEXT, $COLUMN_TEAM_COLOR TEXT)"
        db.execSQL(createTeamDataTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_DATA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TEAM_DATA")
        onCreate(db)
    }

    fun resetTable() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_DATA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TEAM_DATA")
        onCreate(db)
    }

    fun insertUserData(userId: String, teamId: String, teamName: String, teamColor: String): Long {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userId)
        contentValues.put(COLUMN_TEAM_ID, teamId)
        contentValues.put(COLUMN_TEAM_NAME, teamName)
        contentValues.put(COLUMN_TEAM_COLOR, teamColor)
        val result = db.insert(TABLE_USER_DATA, null, contentValues)
        db.close()
        return result
    }

    fun insertTeamData(teamId: String, teamName: String, teamColor: String): Long {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TEAM_ID, teamId)
        contentValues.put(COLUMN_TEAM_NAME, teamName)
        contentValues.put(COLUMN_TEAM_COLOR, teamColor)
        val result = db.insert(TABLE_TEAM_DATA, null, contentValues)
        db.close()
        return result
    }

    private fun getAllUserData(): Cursor? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USER_DATA"
        return db.rawQuery(query, null)
    }

    fun getAllTeamData(): Cursor? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_TEAM_DATA"
        return db.rawQuery(query, null)
    }

    fun getUserData(): UserDatabaseModel? {
        val resultCursor = getAllUserData()
        if (resultCursor != null) {
            if (resultCursor.moveToFirst()) {
                val userIdIndex = resultCursor.getColumnIndex(COLUMN_USER_ID)
                val teamIdIndex = resultCursor.getColumnIndex(COLUMN_TEAM_ID)
                val teamNameIndex = resultCursor.getColumnIndex(COLUMN_TEAM_NAME)
                val teamColorIndex = resultCursor.getColumnIndex(COLUMN_TEAM_COLOR)
                if (userIdIndex >= 0) {
                    return UserDatabaseModel(
                        resultCursor.getString(userIdIndex),
                        resultCursor.getString(teamIdIndex),
                        resultCursor.getString(teamNameIndex),
                        resultCursor.getString(teamColorIndex),
                    )
                }
            }
        }
        return null
    }

    fun getTeamDataList(): List<TeamDatabaseModel>? {
        val resultCursor = getAllTeamData()
        if (resultCursor != null) {
            val teamDataList = mutableListOf<TeamDatabaseModel>()
            while (resultCursor.moveToNext()) {
                val teamIdIndex = resultCursor.getColumnIndex(COLUMN_TEAM_ID)
                val teamNameIndex = resultCursor.getColumnIndex(COLUMN_TEAM_NAME)
                val teamColorIndex = resultCursor.getColumnIndex(COLUMN_TEAM_COLOR)
                val teamData = TeamDatabaseModel(
                        resultCursor.getString(teamIdIndex),
                        resultCursor.getString(teamNameIndex),
                        resultCursor.getString(teamColorIndex),)
                teamDataList.add(teamData)
            }
            return teamDataList
        }
        return null
    }
}
