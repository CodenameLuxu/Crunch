package com.example.basiclogin.Tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class TMEPRDHandler(context: Context): SQLiteOpenHelper(context, TableConstants.TBL_TMEPRD,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val SCRIPT_CREATE = "CREATE TABLE ${TableConstants.TBL_TMEPRD} ( " +
                "${TableConstants.TMEPRD_ID} INTEGER  PRIMARY KEY ," +
                "${TableConstants.TMEPRD_CODE} TEXT," +
                "${TableConstants.TMEPRD_DESC} TEXT" + ");"

        db?.execSQL(SCRIPT_CREATE)
        Log.i("Init"," TMEPRD table created")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}