package com.example.basiclogin.Tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class EATHISTHandler(context: Context): SQLiteOpenHelper(context, TableConstants.TBL_EATHIST,null,1) {
    val TAG = "EATHISTHandler"
    override fun onCreate(db: SQLiteDatabase?) {
        val SCRIPT_CREATE = "CREATE TABLE ${TableConstants.TBL_EATHIST} ( " +
                "${TableConstants.EATHIST_ID} INTEGER  PRIMARY KEY ," +
                "${TableConstants.EATHIST_USERID} INTEGER," +
                "${TableConstants.EATHIST_DISHID} TEXT," +
                "${TableConstants.EATHIST_TMEID} TEXT," +
                "${TableConstants.EATHIST_DATE} INTEGER" + ");"
        db?.execSQL(SCRIPT_CREATE)
        Log.i(TAG ,"${TableConstants.TBL_EATHIST} table created")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}