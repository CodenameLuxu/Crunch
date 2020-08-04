package com.example.basiclogin.Application

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.basiclogin.R
import com.example.basiclogin.Statics


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))
        val userid=intent.getStringExtra(Statics.KEY_USERID)
        Log.i(TAG, "User id : $userid")
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            setContentView(R.layout.activity_main)
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        val userid=intent.getStringExtra(Statics.KEY_USERID)
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString(Statics.KEY_USERID,userid)
    }

}