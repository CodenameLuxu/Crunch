package com.example.basiclogin.Tables
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.basiclogin.Service.ConnectionService
import com.example.basiclogin.ui.login.UserCredential
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.DocumentCodecProvider


import org.bson.conversions.Bson


class USRADM(val context:Context) {
    private lateinit var database : USRADMHandler
    private lateinit var table : MongoCollection<UserCredential>
    private val TAG  ="USRADM Table"
    init {

        database = USRADMHandler(context)
//        table = ConnectionService.getConnection().getCollection(TableConstants.TBL_USRADM,UserCredential::class.java)
    }

    public fun isUserExist(username:String):Boolean {
        var userlist : MutableList<UserCredential> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_USRADM}" +
        " WHERE ${TableConstants.USRADM_USRUSRNM}  = \'$username\' "

        val dbread = database.readableDatabase
        val result  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (result.moveToFirst()){
            do {
                val current = UserCredential(
                    result.getString(0).toInt(),
                    result.getString(1),
                    result.getString(2)
                )
                userlist.add(current)
            }while(result.moveToNext())

        }
        result.close()
        dbread.close()
        outputCredentials(userlist)
        return userlist.size > 0

////        MongoDB
//        val filter : Bson = Filters.eq(TableConstants.USRADM_USRUSRNM, username)
//        val result : FindIterable<UserCredential> = table.find(filter)
//        return result.count() > 0

    }

    public fun isCredentialExist(user:String,password:String): Boolean{
        var userlist : MutableList<UserCredential> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_USRADM}" +
        " WHERE ${TableConstants.USRADM_USRUSRNM}  = \"${user}\" "+
        " AND ${TableConstants.USRADM_usrpass} = \"${password}\" "
        val dbread = database.readableDatabase
        val result  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (result.moveToFirst()){
            do {
                val current = UserCredential(
                    result.getString(0).toInt(),
                    result.getString(1),
                    result.getString(2)
                )
                userlist.add(current)
            }while(result.moveToNext())

        }
        result.close()
        dbread.close()
        return userlist.size > 0

        ////        MongoDB
//        val userfilter : Bson = Filters.eq(TableConstants.USRADM_USRUSRNM, user)
//        val passwordfilter : Bson = Filters.eq(TableConstants.USRADM_usrpass, password)
//        val result : FindIterable<UserCredential> = table.find().filter(userfilter).filter(passwordfilter)
//        return result.count() > 0
    }

    public fun AddUser(entry:UserCredential):Boolean{
        try{
            val userid = generateUserID()
            if (userid == -1){
              throw Exception(" Fail to generate user id ")
            }

            entry.usrid = userid

            val writedb = database.writableDatabase
            var cv = ContentValues()
            cv.put(TableConstants.USRADM_USRID, entry.usrid )
            cv.put(TableConstants.USRADM_USRUSRNM, entry.usrusrnm )
            cv.put(TableConstants.USRADM_usrpass, entry.usrpass )
            val result = writedb.insert(TableConstants.TBL_USRADM,null,cv)
            if(result == -1.toLong()){
                Toast.makeText(context,"Failure to insert new user", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"Registration completed", Toast.LENGTH_LONG).show()
            }
//            table.insertOne(entry)
            return true
        }catch(e: Exception){
            Log.i(TAG, "AddUser() --> Exception found : ${e.message}")
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show()
            return false
        }

    }

    public fun generateUserID():Int{
        try {
            val QUERY_SCRIPT = "SELECT IFNULL(MAX(${TableConstants.USRADM_USRID}) + 1,0) FROM ${TableConstants.TBL_USRADM} ;"
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

            //        MongoDB
//            val sortFilter = BasicDBObject(TableConstants.USRADM_USRID,-1)
//            val result = table.find().sort(sortFilter)
//            val returnid = result.first().usrid + 1
//            Log.i(TAG, "Key generated : ${returnid}")
            return returnid
        }catch(e: Exception){
            Log.i(TAG, "generateUserID() -- > Exception found : ${e.message}")
            return 0
        }
    }

    public fun outputCredentials(list : List<UserCredential>){
        for (user in list){
            Log.i(TAG,"Out --> ${user.usrid},${user.usrusrnm},${user.usrpass}")
        }
    }

    public fun getUserByID(id:Int):  UserCredential{
        var userlist : MutableList<UserCredential> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_USRADM}" +
                " WHERE ${TableConstants.USRADM_USRID}  = $id "

        val dbread = database.readableDatabase
        val result  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (result.moveToFirst()){
            do {
                val current  = UserCredential(
                    result.getString(0).toInt(),
                    result.getString(1),
                    result.getString(2)
                )
                userlist.add(current)
            }while(result.moveToNext())

        }
        result.close()
        dbread.close()
    return userlist.get(0)
//        MongoDB
//        val query =  Filters.eq(TableConstants.USRADM_USRID, id)
//        val result = table.find(query).first()
//        return result!!
    }

    public fun getUserByUsername(username:String):  UserCredential{
        var userlist : MutableList<UserCredential> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${TableConstants.TBL_USRADM}" +
                " WHERE ${TableConstants.USRADM_USRUSRNM}  = \'${username}\' "

        val dbread = database.readableDatabase
        val result  = dbread.rawQuery(QUERY_SCRIPT,null)
        if (result.moveToFirst()){
            do {
                val current  = UserCredential(
                    result.getString(0).toInt(),
                    result.getString(1),
                    result.getString(2)
                )
                userlist.add(current)
            }while(result.moveToNext())

        }
        result.close()
        dbread.close()
        return userlist[0]

//        MongoDB
//        val query =  Filters.eq(TableConstants.USRADM_USRUSRNM, username)
//        val result = table.find(query).first()
//        return result!!
    }



}