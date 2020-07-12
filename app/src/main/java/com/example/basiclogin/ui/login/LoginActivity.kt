package com.example.basiclogin.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.basiclogin.R
import com.example.basiclogin.Statics
//import com.example.basiclogin.Application.MainActivity
import com.example.basiclogin.Application.MainActivity
import com.example.basiclogin.Tables.USRADM
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {

    private val TAG = "Login Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.E01_Btn_login).setOnClickListener{
             if (processCredential()){
                 toMainPage(findViewById<EditText>(R.id.E01_In_Username).text.toString())
                 reset()
             }
        }

        findViewById<Button>(R.id.E01_Btn_Register).setOnClickListener {
            toRegisterPage()
        }

    }

    private fun processCredential():Boolean{
        try {
            val username =  findViewById<EditText>(R.id.E01_In_Username).text.toString()
            val password = findViewById<EditText>(R.id.E01_In_Password).text.toString()
            val validuser = USRADM(this).isCredentialExist(username,password)
            if (validuser) {
                Log.i(TAG,"User found")
//                Toast.makeText(this,"User Found",Toast.LENGTH_LONG).show()

                return true
            }else{
                Log.i(TAG,"user not found")
                Toast.makeText(this,"User not found",Toast.LENGTH_LONG)
                return false
            }
        }catch (e: Exception){
            Log.i(TAG,"Exception found : ${e.message}")
            return false
        }

    }

    private fun reset(){
        findViewById<EditText>(R.id.E01_In_Username).setText("")
        findViewById<EditText>(R.id.E01_In_Password).setText("")
    }

    private fun toRegisterPage(){
        val intent = Intent(this, RegisterActivty::class.java)
        startActivity(intent)
        finish()
    }

    private fun toMainPage(username:String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Statics.USRADM_USRUSRNM,username)
        startActivity(intent)
    }



}
