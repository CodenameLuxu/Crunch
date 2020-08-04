package com.example.basiclogin.Tables

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.basiclogin.Models.CuisineCategoryRecord
import com.example.basiclogin.Models.DishItemRecord

class DSHITM(val context: Context) :GenericTableInterface<DishItemRecord>{
    private lateinit var database: DSHITMHandler
    private val TAG = "DSHITM"

    init {
        database = DSHITMHandler(context)

    }
    override fun isCodeExist(code:String):Boolean{
        var result : MutableList<DishItemRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_DSHITM}" +
                " WHERE ${TableConstants.DSHITM_DHIITMCD}  = \'$code\' "

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = DishItemRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(1),
                    queryoutput.getString(2),
                    queryoutput.getString(3).toInt(),
                    queryoutput.getString(4).toInt() == 1,
                    queryoutput.getString(5).toInt() == 1,
                    queryoutput.getString(6).toInt() == 1,
                    queryoutput.getString(7).toInt() == 1
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
        val QUERY_SCRIPT = "SELECT IFNULL(MAX(${TableConstants.DSHITM_ID}) + 1,0) FROM ${TableConstants.TBL_DSHITM} ;"
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

    fun generateItemCode(entry: DishItemRecord):String{

        val header = entry.crucat.toString().padStart(2, '0')

        val QUERY_SCRIPT = "SELECT IFNULL(MAX(${TableConstants.DSHITM_DHIITMCD}) ,'0') FROM ${TableConstants.TBL_DSHITM} " +
                "WHERE ${TableConstants.DSHITM_DHICRUCAT} LIKE '${header}%'"
        val dbread = database.readableDatabase
        val result = dbread.rawQuery(QUERY_SCRIPT, null)
        var returncode: String = ""
        if (result.moveToFirst()) {
            do {
                returncode = result.getString(0)
            } while (result.moveToNext())

        }
        result.close()
        dbread.close()

        returncode = returncode.padStart(5, '0')
        returncode = header + returncode
        Log.i(TAG, "Key generated : ${returncode}")
        return returncode
    }

    override fun addRecord(entry: DishItemRecord):Boolean{
        if (isCodeExist(entry.code)){
            throw Exception("Code already exist")
            return false
        }

        entry.id = generateRecordID()
        entry.code = generateItemCode(entry)

        val writedb = database.writableDatabase
        var cv = ContentValues()
        cv.put(TableConstants.DSHITM_ID, entry.id )
        cv.put(TableConstants.DSHITM_DHIITMCD, entry.code )
        cv.put(TableConstants.DSHITM_DHIITMNM, entry.name)
        cv.put(TableConstants.DSHITM_DHICRUCAT, entry.crucat)
        cv.put(TableConstants.DSHITM_VEGANOPT,if (entry.veganOpt) 1 else 0  )
        cv.put(TableConstants.DSHITM_NUTFREOPT, if (entry.nutFreeOpt) 1 else 0 )
        cv.put(TableConstants.DSHITM_MILKFREOPT, if (entry.milkFreeOpt) 1 else 0 )
        cv.put(TableConstants.DSHITM_SPICYOPT, if (entry.spicyOpt) 1 else 0 )
        val result = writedb.insert(TableConstants.TBL_DSHITM,null,cv)
        return (result != -1.toLong())
    }

    override fun updateRecord(entry: DishItemRecord):Boolean{
        val writedb = database.writableDatabase
        var cv = ContentValues()
        cv.put(TableConstants.DSHITM_ID, entry.id )
        cv.put(TableConstants.DSHITM_DHIITMCD, entry.code )
        cv.put(TableConstants.DSHITM_DHIITMNM, entry.name)
        cv.put(TableConstants.DSHITM_DHICRUCAT, entry.crucat)
        cv.put(TableConstants.DSHITM_VEGANOPT,if (entry.veganOpt) 1 else 0  )
        cv.put(TableConstants.DSHITM_NUTFREOPT, if (entry.nutFreeOpt) 1 else 0 )
        cv.put(TableConstants.DSHITM_MILKFREOPT, if (entry.milkFreeOpt) 1 else 0 )
        cv.put(TableConstants.DSHITM_SPICYOPT, if (entry.spicyOpt) 1 else 0 )
        val result = writedb.update(TableConstants.TBL_DSHITM,cv,"${TableConstants.DSHITM_ID} = ${entry.id}",null)
        return result >=0
    }

    override fun deleteRecord(entry: DishItemRecord): Boolean{
        val result =  database.writableDatabase.delete(
            TableConstants.TBL_DSHITM,
            TableConstants.DSHITM_ID + "=" + entry.id,
            null
        )
        return result > 0
    }

    override fun outputRecords(records: List<DishItemRecord>){
        for (entry in records){
            Log.i(TAG,"Out --> ${entry.id},${entry.code},${entry.name},${entry.veganOpt},${entry.nutFreeOpt},${entry.milkFreeOpt},${entry.spicyOpt}")
        }
    }

    override fun fetchAllRecord():List<DishItemRecord>{
        var result : MutableList<DishItemRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_DSHITM}"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = DishItemRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(2),
                    queryoutput.getString(3),
                    queryoutput.getString(1).toInt(),
                    queryoutput.getString(4).toInt() == 1,
                    queryoutput.getString(5).toInt() == 1,
                    queryoutput.getString(6).toInt() == 1,
                    queryoutput.getString(7).toInt() == 1
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        return result
    }

    fun getRecordWithCategory(cat: Int):List<DishItemRecord>{
        var result : MutableList<DishItemRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_DSHITM} " +
                "WHERE ${TableConstants.DSHITM_DHICRUCAT} = $cat"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = DishItemRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(2),
                    queryoutput.getString(3),
                    queryoutput.getString(1).toInt(),
                    queryoutput.getString(4).toInt() == 1,
                    queryoutput.getString(5).toInt() == 1,
                    queryoutput.getString(6).toInt() == 1,
                    queryoutput.getString(7).toInt() == 1
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        return result
    }

    override fun getRecordByID(id : Int): DishItemRecord {
        var result : MutableList<DishItemRecord> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_DSHITM} " +
                "WHERE ${TableConstants.DSHITM_ID} = $id"

        val dbread = database.readableDatabase
        val queryoutput  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (queryoutput.moveToFirst()){
            do {
                val current = DishItemRecord(
                    queryoutput.getString(0).toInt(),
                    queryoutput.getString(2),
                    queryoutput.getString(3),
                    queryoutput.getString(1).toInt(),
                    queryoutput.getString(4).toInt() == 1,
                    queryoutput.getString(5).toInt() == 1,
                    queryoutput.getString(6).toInt() == 1,
                    queryoutput.getString(7).toInt() == 1
                )
                result.add(current)
            }while(queryoutput.moveToNext())

        }
        queryoutput.close()
        dbread.close()
        return result[0]
    }

}