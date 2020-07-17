package com.example.basiclogin.Tables
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.basiclogin.Models.TimePeriodRecord

class TMEPRD(val context:Context) : GenericTableInterface<TimePeriodRecord>{
    private lateinit var database : TMEPRDHandler
    private val TAG  ="TMEPRD Table"

    init {
        database = TMEPRDHandler(context)
    }

    override fun isCodeExist(code:String):Boolean {
        var result : MutableList<TimePeriodRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_TMEPRD}" +
                " WHERE ${TableConstants.TMEPRD_CODE}  = \'$code\' "

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = TimePeriodRecord(
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
        try {
            val QUERY_SCRIPT = "SELECT MAX(${TableConstants.TMEPRD_ID}) + 1 FROM ${TableConstants.TBL_TMEPRD} ;"
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
        }catch(e: Exception){
            Log.i(TAG, "generateUserID() -- > Exception found : ${e.message}")
            return 0
        }
    }

    override fun addRecord(entry:TimePeriodRecord):Boolean{
        try{
            if (isCodeExist(entry.code)){
                throw Exception("code already exust")
            }
            val recordid = generateRecordID()
            if (recordid == -1){
                throw Exception(" Fail to generate user id ")
            }

            entry.id = recordid

            val writedb = database.writableDatabase
            var cv = ContentValues()
            cv.put(TableConstants.TMEPRD_ID, entry.id )
            cv.put(TableConstants.TMEPRD_CODE, entry.code )
            cv.put(TableConstants.TMEPRD_DESC, entry.desc )
            val result = writedb.insert(TableConstants.TBL_TMEPRD,null,cv)
            if(result == -1.toLong()){
                Toast.makeText(context,"Failure to insert new user", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"Registration completed", Toast.LENGTH_LONG).show()
            }
            return true
        }catch(e: Exception){
            Log.i(TAG, "AddRecord() --> Exception found : ${e.message}")
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show()
            return false
        }
    }



    override fun updateRecord(entry:TimePeriodRecord):Boolean{
        try{
            val writedb = database.writableDatabase
            var cv = ContentValues()
            cv.put(TableConstants.TMEPRD_ID, entry.id )
            cv.put(TableConstants.TMEPRD_CODE, entry.code )
            cv.put(TableConstants.TMEPRD_DESC, entry.desc )
            val result = writedb.update(TableConstants.TBL_TMEPRD,cv,"${TableConstants.TMEPRD_ID} = ${entry.id}",null)
            if(result <= 0){
                Toast.makeText(context,"Failure to update time period record", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"time period record updated", Toast.LENGTH_LONG).show()
            }
            return true
        }catch(e: Exception){
            Log.i(TAG, "updateRecord() --> Exception found : ${e.message}")
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show()
            return false
        }
    }

    override fun deleteRecord(entry: TimePeriodRecord): Boolean {
        try {
            val result =  database.writableDatabase.delete(
                TableConstants.TBL_TMEPRD,
                TableConstants.TMEPRD_ID + "=" + entry.id,
                null
            )
            if ( result < 1 ){
                throw(Exception("Delete : No row is affected"))
            }else{
                Log.i(TAG,"${result} rows deleted")
            }

            return true
        }catch(e :Exception){
            Log.i(TAG,"Exception found: ${e.message}")
            return false
        }
    }

    override fun outputRecords(list : List<TimePeriodRecord>){
        for (entry in list){
            Log.i(TAG,"Out --> ${entry.id},${entry.code},${entry.desc}")
        }
    }

//    General query

    override fun fetchAllRecord():List<TimePeriodRecord> {
        var result : MutableList<TimePeriodRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_TMEPRD}"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = TimePeriodRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1),
                    queryoutput.getString(2)
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
//        outputRecords(result)
        Log.i(TAG, "FetchAllRecord --> returned ${result.size}")
        return result
    }


}