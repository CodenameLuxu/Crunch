package com.example.basiclogin.Tables

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.basiclogin.Models.CuisineCategoryRecord


class CRUCAT(val context: Context) :GenericTableInterface<CuisineCategoryRecord>{
    private lateinit var database: CRUCATHandler
    private val TAG = "CRUCAT"

    init {
        database = CRUCATHandler(context)

    }

    override fun isCodeExist(code:String):Boolean {
        var result : MutableList<CuisineCategoryRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_CRUCAT}" +
                " WHERE ${TableConstants.CRUCAT_CODE}  = \'$code\' "

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = CuisineCategoryRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1),
                    queryoutput.getString(2)
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        outputRecords(result)
        return result.size > 0
    }

    override fun generateRecordID():Int{
        val QUERY_SCRIPT = "SELECT IFNULL(MAX(${TableConstants.CRUCAT_ID}) + 1,0) FROM ${TableConstants.TBL_CRUCAT} ;"
        val dbread = database.readableDatabase
        val result = dbread.rawQuery(QUERY_SCRIPT, null)
        var returnid: Int = -1
        if (result.moveToFirst()) {
            do {
                returnid = result.getString(0).toInt()
            } while (result.moveToNext())

        }
        result.close()
        dbread.close()
        Log.i(TAG, "Key generated : ${returnid}")
        return returnid
    }

    override fun addRecord(entry: CuisineCategoryRecord):Boolean{
        if (isCodeExist(entry.code)){
            throw Exception("code already exust")
        }
        val recordid = generateRecordID()
        entry.id = recordid

        val writedb = database.writableDatabase
        var cv = ContentValues()
        cv.put(TableConstants.CRUCAT_ID, entry.id )
        cv.put(TableConstants.CRUCAT_CODE, entry.code )
        cv.put(TableConstants.CRUCAT_DESC, entry.desc )
        val result = writedb.insert(TableConstants.TBL_CRUCAT,null,cv)
        return (result != -1.toLong())

    }


    override fun updateRecord(entry: CuisineCategoryRecord):Boolean{
        val writedb = database.writableDatabase
        var cv = ContentValues()
        cv.put(TableConstants.CRUCAT_ID, entry.id )
        cv.put(TableConstants.CRUCAT_CODE, entry.code )
        cv.put(TableConstants.CRUCAT_DESC, entry.desc )
        val result = writedb.update(TableConstants.TBL_CRUCAT,cv,"${TableConstants.CRUCAT_ID} = ${entry.id}",null)
        return result >=0

    }

    override fun deleteRecord(entry: CuisineCategoryRecord): Boolean {
        val result =  database.writableDatabase.delete(
            TableConstants.TBL_CRUCAT,
            TableConstants.CRUCAT_ID + "=" + entry.id,
            null
        )
        return result > 0
    }

    override fun outputRecords(list : List<CuisineCategoryRecord>){
        for (entry in list){
            Log.i(TAG,"Out --> ${entry.id},${entry.code},${entry.desc}")
        }
    }

//    General query

    override fun fetchAllRecord():List<CuisineCategoryRecord> {
        var result : MutableList<CuisineCategoryRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_CRUCAT}"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = CuisineCategoryRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1),
                    queryoutput.getString(2)
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        return result
    }

}