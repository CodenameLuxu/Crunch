package com.example.basiclogin.ui.login

//import com.example.basiclogin.Application.MainActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.basiclogin.Application.MainActivity
import com.example.basiclogin.R
import com.example.basiclogin.Statics
import com.example.basiclogin.Tables.USRADM


class LoginActivity : AppCompatActivity() {

    private val TAG = "Login Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        findViewById<Button>(R.id.E01_Btn_login).setOnClickListener{
             if (processCredential()){
                 toMainPage(findViewById<EditText>(R.id.E01_In_Username).text.toString())
                 reset()
             }else{
                 Toast.makeText(this,"User not found",Toast.LENGTH_LONG)
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
    }

    private fun toMainPage(username:String){
        val userobj : UserCredential = USRADM(this).getUserByUsername(username)
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        intent.putExtra(Statics.KEY_USERID, userobj.usrid.toString())
//        Log.i(TAG,"${userobj.usrusrnm} : ${userobj.usrid}  passed")
//
//
//        val editor : SharedPreferences.Editor = getSharedPreferences(Statics.KEY_USEROBJ, Context.MODE_PRIVATE).edit()
//        editor.putString(S);
//        editor.commit();
//        intent

        startActivity(intent)
    }



}
