package com.example.basiclogin.Tables

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import com.example.basiclogin.Models.CuisineCategoryRecord

class TableInterface {
}


interface GenericTableInterface<E> {

    public fun isCodeExist(code:String):Boolean

    public fun generateRecordID():Int

    public fun addRecord(entry: E):Boolean

    public fun updateRecord(entry: E):Boolean

    public fun deleteRecord(entry: E): Boolean

    public fun outputRecords(records: List<E>)

    public fun fetchAllRecord():List<E>
    public fun getRecordByID(id : Int):E

}