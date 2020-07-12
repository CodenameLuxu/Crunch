package com.example.basiclogin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.basiclogin.R
import com.example.basiclogin.Statics
import com.example.basiclogin.Tables.USRADM
import com.google.firebase.database.*
import java.lang.Exception

class RegisterActivty : AppCompatActivity() {
    private val TAG = "Activity Register"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<Button>(R.id.E02_Btn_Submit).setOnClickListener {
            if (isValidInput()){
                registerUser()
            }else{
                Toast.makeText(this,"Invalid username or password",Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.E02_Btn_Back).setOnClickListener {
            toLoginPage()
        }

    }


    private fun  isValidInput(): Boolean{
        return  findViewById<EditText>(R.id.E02_In_Username).text.isNotBlank() and findViewById<EditText>(R.id.E02_In_Password).text.isNotBlank()
    }

    private fun registerUser(){
        val username = findViewById<EditText>(R.id.E02_In_Username).text.toString()
        val password = findViewById<EditText>(R.id.E02_In_Password).text.toString()
        val credential = username+"_"+password

        if (USRADM(this).isUserExist(username)){
            Log.i(TAG,"User already Exist")
        }else{
            Log.i(TAG,"Creating user")
            try {
                val new_entry = UserCredential(0,username,password)
                USRADM(this).AddUser(new_entry)
                Toast.makeText(this,"Registration complete", Toast.LENGTH_LONG).show()
                resetInput()
            }catch (e: Exception){
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
                Log.i(TAG,"Exception found : ${e.message}")
            }
        }
    }

    private fun toLoginPage(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun resetInput(){
        findViewById<EditText>(R.id.E02_In_Username).setText("")
        findViewById<EditText>(R.id.E02_In_Password).setText("")
    }
}