package com.example.basiclogin.Tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DSHITMHandler (context: Context): SQLiteOpenHelper(context, TableConstants.TBL_DSHITM,null,1) {
    val TAG = "DSHITMHandler"
    override fun onCreate(db: SQLiteDatabase?) {
        val SCRIPT_CREATE = "CREATE TABLE ${TableConstants.TBL_DSHITM} ( " +
                "${TableConstants.DSHITM_ID} INTEGER  PRIMARY KEY ," +
                "${TableConstants.DSHITM_DHIITMCD} TEXT," +
                "${TableConstants.DSHITM_DHIITMNM} TEXT," +
                "${TableConstants.DSHITM_VEGANOPT} INTEGER," +
                "${TableConstants.DSHITM_MILKFREOPT} INTEGER," +
                "${TableConstants.DSHITM_NUTFREOPT} INTEGER," +
                "${TableConstants.DSHITM_SPICYOPT} INTEGER" + ");"

        Log.i(TAG,"SCRIPT : ${SCRIPT_CREATE}")
        db?.execSQL(SCRIPT_CREATE)
        Log.i(TAG ,"${TableConstants.TBL_DSHITM} table created")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}