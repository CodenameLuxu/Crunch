package com.example.basiclogin.Tables
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.basiclogin.Statics
import com.example.basiclogin.ui.login.UserCredential


class USRADM(val context:Context) {
    private lateinit var database : USRADMHandler
    private val TAG  ="USRADM Table"

    init {
        database = USRADMHandler(context)
    }

    public fun isUserExist(username:String):Boolean {
        var userlist : MutableList<UserCredential> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${Statics.TBL_USRADM}" +
        " WHERE ${Statics.USRADM_USRUSRNM}  = \'$username\' "

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
    }

    public fun isCredentialExist(user:String,password:String): Boolean{
        var userlist : MutableList<UserCredential> = ArrayList()
        val QUERY_SCRIPT  = "SELECT * FROM ${Statics.TBL_USRADM}"
        "WHERE ${Statics.USRADM_USRUSRNM}  = \"$user\" "
        " AND ${Statics.USRADM_usrpass} = \"${password}\" "
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
            cv.put(Statics.USRADM_USRID, entry.usrid )
            cv.put(Statics.USRADM_USRUSRNM, entry.usrusrnm )
            cv.put(Statics.USRADM_usrpass, entry.usrpass )
            val result = writedb.insert(Statics.TBL_USRADM,null,cv)
            if(result == -1.toLong()){
                Toast.makeText(context,"Failure to insert new user", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"Registration completed", Toast.LENGTH_LONG).show()
            }
            return true
        }catch(e: Exception){
            Log.i(TAG, "AddUser() --> Exception found : ${e.message}")
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show()
            return false
        }

    }

    public fun generateUserID():Int{
        try {
            val QUERY_SCRIPT = "SELECT MAX(${Statics.USRADM_USRID}) + 1 FROM ${Statics.TBL_USRADM} ;"
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

    public fun outputCredentials(list : List<UserCredential>){
        for (user in list){
            Log.i(TAG,"Out --> ${user.usrid},${user.usrusrnm},${user.usrpass}")
        }
    }



}