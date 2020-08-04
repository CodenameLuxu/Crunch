package com.example.basiclogin.Service

import android.util.Log
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase


class ConnectionService {
    companion object {
        val TAG = "ConnectionService"
        fun getConnection(): MongoDatabase {
            Log.i(TAG,"Getting connection")
            val uri = MongoClientURI("mongodb+srv://admin:admin@cluster0.ihfr5.mongodb.net/CrunchDB?retryWrites=true&w=majority")
            Log.i(TAG,"Getting Client")
            val mongoClient = MongoClient(uri)
            Log.i(TAG,"Getting Database")
            val db = mongoClient.getDatabase("CrunchDB")
            return db
        }
    }
}