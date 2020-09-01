package com.example.basiclogin.Tables

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.basiclogin.Models.DishItemRecord
import com.example.basiclogin.Models.EatHistoryRecord
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EATHIST(val context: Context) :GenericTableInterface<EatHistoryRecord>{
    private lateinit var database: EATHISTHandler
    private val TAG = "EATHIST"

    init {
        database = EATHISTHandler(context)
    }

    override fun isCodeExist(code:String):Boolean{
        return false
    }

    override fun generateRecordID():Int{
        val QUERY_SCRIPT = "SELECT IFNULL(MAX(${TableConstants.EATHIST_ID}) + 1,0) FROM ${TableConstants.TBL_EATHIST} ;"
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
        return returnid
    }

    override fun addRecord(entry: EatHistoryRecord):Boolean{
        entry.id = generateRecordID()

        val writedb = database.writableDatabase
        var cv = ContentValues()
        cv.put(TableConstants.EATHIST_ID, entry.id )
        cv.put(TableConstants.EATHIST_USERID, entry.userid )
        cv.put(TableConstants.EATHIST_DISHID, entry.dishid)
        cv.put(TableConstants.EATHIST_TMEID, entry.timeperiodid)
        cv.put(TableConstants.EATHIST_DATE, entry.date )
        val result = writedb.insert(TableConstants.TBL_EATHIST,null,cv)
        return (result != -1.toLong())
    }

    override fun updateRecord(entry: EatHistoryRecord):Boolean{
        val writedb = database.writableDatabase
        var cv = ContentValues()
        cv.put(TableConstants.EATHIST_ID, entry.id )
        cv.put(TableConstants.EATHIST_USERID, entry.userid )
        cv.put(TableConstants.EATHIST_DISHID, entry.dishid)
        cv.put(TableConstants.EATHIST_TMEID, entry.timeperiodid)
        cv.put(TableConstants.EATHIST_DATE, entry.date )
        val result = writedb.update(TableConstants.TBL_EATHIST,cv,"${TableConstants.EATHIST_ID} = ${entry.id}",null)
        return result >=0
    }

    override fun deleteRecord(entry: EatHistoryRecord): Boolean{
        val result =  database.writableDatabase.delete(
            TableConstants.TBL_EATHIST,
            "${TableConstants.EATHIST_ID} = ${entry.id} AND ${TableConstants.EATHIST_USERID} = ${entry.userid}",
            null
        )
        return result > 0
    }

    override fun fetchAllRecord():List<EatHistoryRecord>{
        var result : MutableList<EatHistoryRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_EATHIST}"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = EatHistoryRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1).toInt(),
                    queryoutput.getString(2).toInt(),
                    queryoutput.getString(3).toInt(),
                    queryoutput.getString(4)
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        return result
    }


     fun fetchAllRecordByUser(userid: Int):List<EatHistoryRecord>{
        var result : MutableList<EatHistoryRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_EATHIST} "+
                " WHERE ${TableConstants.EATHIST_USERID} = ${userid}"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = EatHistoryRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1).toInt(),
                    queryoutput.getString(2).toInt(),
                    queryoutput.getString(3).toInt(),
                    queryoutput.getString(4)
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        return result
    }

    override fun getRecordByID(id : Int):EatHistoryRecord{
        var result : MutableList<EatHistoryRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_EATHIST} "+
                "WHERE ${TableConstants.EATHIST_ID} = $id"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = EatHistoryRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1).toInt(),
                    queryoutput.getString(2).toInt(),
                    queryoutput.getString(3).toInt(),
                    queryoutput.getString(4)
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        return result[0]

    }

    override fun outputRecords(records: List<EatHistoryRecord>){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun getRecommendation(userid: Int): DishItemRecord {
        var result : MutableList<EatHistoryRecord> = ArrayList()

        val current = LocalDateTime.now().minusDays(30)
        val formatter = DateTimeFormatter.ofPattern(FormatConstants.F_Date_Store)
        val fromDate = current.format(formatter)

        val SEARCH_SCRIPT = " SELECT  ${TableConstants.EATHIST_USERID},  ${TableConstants.EATHIST_DISHID}, COUNT(1) "  +
                                    " FROM ${TableConstants.TBL_EATHIST} " +
                             " WHERE ${TableConstants.EATHIST_DATE} > ${fromDate} " +
                            " GROUP BY ${TableConstants.EATHIST_USERID},  ${TableConstants.EATHIST_DISHID} " +
                            " ORDER BY COUNT(1) ASC "
         val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(SEARCH_SCRIPT,null)
        val initial : ArrayList<ArrayList<Int>> = ArrayList()
        if (queryoutput.moveToFirst()){
            do {
                val current = ArrayList<Int>()
                current.add(queryoutput.getString(0).toInt())
                current.add(queryoutput.getString(1).toInt())
                current.add(queryoutput.getString(2).toInt())
                initial.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()

        if( initial.isEmpty()){
            return getLatestEatenDish(userid)
        }else{
            return DSHITM(context).getRecordByID(initial.get(0).get(1))
        }



    }

    public fun getLatestEatenDish(id : Int): DishItemRecord{
        var result : MutableList<EatHistoryRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_EATHIST} "+
                " WHERE ${TableConstants.EATHIST_ID} = $id " +
                " ORDER BY ${TableConstants.EATHIST_DATE} DESC "

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = EatHistoryRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1).toInt(),
                    queryoutput.getString(2).toInt(),
                    queryoutput.getString(3).toInt(),
                    queryoutput.getString(4)
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()

        val latestid = result.get(0).dishid
        return DSHITM(context).getRecordByID(latestid)




    }

}