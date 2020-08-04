package com.example.basiclogin.Application

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.R
import com.example.basiclogin.Statics
import com.example.basiclogin.Tables.USRADM


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class F01_Fragment : Fragment() {
    val TAG = "F01 Fragment"
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f01_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userid =  this.activity?.intent?.getStringExtra(Statics.KEY_USERID)?.toInt()

        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.F01_BTN_Log).setOnClickListener {
            findNavController().navigate(R.id.action_f01_Fragment_to_f02_Fragment)
        }

        view.findViewById<Button>(R.id.F01_BTN_Picker).setOnClickListener {
            findNavController().navigate(R.id.action_f01_Fragment_to_f03_Fragment)
        }

        view.findViewById<Button>(R.id.F01_Btn_Option).setOnClickListener {
            findNavController().navigate(R.id.action_f01_Fragment_to_f90_Fragment)
        }
        Log.i(TAG ,"User passed : ${userid}")
        try {
            val userobj = USRADM(context!!).getUserByID(userid!!)
            Toast.makeText(context, "Greetings, ${userobj.usrusrnm}", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.i(TAG,"Exception found : ${e.message}")
            Toast.makeText(context, "No user found", Toast.LENGTH_LONG).show()
        }



    }

}