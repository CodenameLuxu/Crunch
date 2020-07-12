package com.example.basiclogin.Tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class USRADMHandler(context:Context): SQLiteOpenHelper(context, TableConstants.TBL_USRADM,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val SCRIPT_CREATE = "CREATE TABLE ${TableConstants.TBL_USRADM} ( " +
            "${TableConstants.USRADM_USRID} INTEGER  PRIMARY KEY ," +
            "${TableConstants.USRADM_USRUSRNM} TEXT," +
           " ${TableConstants.USRADM_usrpass} TEXT" + ");"

        db?.execSQL(SCRIPT_CREATE)
        Log.i("Init"," USRADM  table created")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}