package com.example.basiclogin.Application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.FirebaseDatabase
import com.example.basiclogin.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))

        val db : FirebaseDatabase = FirebaseDatabase.getInstance();
        val db_ref = db.getReference("Message")
        db_ref.setValue("Hello World!! Im menu:)");


    }

}