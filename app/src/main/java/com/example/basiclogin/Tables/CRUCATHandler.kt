package com.example.basiclogin.Tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CRUCATHandler(context: Context): SQLiteOpenHelper(context, TableConstants.TBL_CRUCAT,null,1) {
    val TAG = "CRUCATHandler"
    override fun onCreate(db: SQLiteDatabase?) {
        val SCRIPT_CREATE = "CREATE TABLE ${TableConstants.TBL_CRUCAT} ( " +
                "${TableConstants.CRUCAT_ID} INTEGER  PRIMARY KEY ," +
                "${TableConstants.CRUCAT_CODE} TEXT," +
                "${TableConstants.CRUCAT_DESC} TEXT" + ");"

        Log.i(TAG,"SCRIPT : ${SCRIPT_CREATE}")
        db?.execSQL(SCRIPT_CREATE)
        Log.i(TAG ,"${TableConstants.TBL_CRUCAT} table created")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}