package com.example.basiclogin.Application

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.Models.TimePeriodRecord
import com.example.basiclogin.Models.TimePeriodRecordAdapter
import com.example.basiclogin.R
import com.example.basiclogin.Tables.TMEPRD
import kotlinx.android.synthetic.main.fragment_f91__config_tmprd.*


class F91_Fragment : Fragment() {
    private val TAG = "F91 Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f91__config_tmprd, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.F91_Btn_Add).setOnClickListener {
            try {
                if (F91_In_TMEPRD_Cd.text.isEmpty() || F91_In_TMEPRD_Desc.text.isEmpty()) {
                    Toast.makeText(activity, "All fields must not be empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.i(TAG, "Attempting to add record")
                    val toAdd = TimePeriodRecord(
                        0,
                        F91_In_TMEPRD_Cd.text.toString(),
                        F91_In_TMEPRD_Desc.text.toString()
                    )
                    TMEPRD(context!!).addRecord(toAdd)
                }
                Log.i(TAG, "Adding operation finished.")
                Toast.makeText(context,"Record added",Toast.LENGTH_LONG).show()
                refreshResultView()
            }catch(e: Exception){
                Toast.makeText(context,"Something gone wrong",Toast.LENGTH_LONG).show()
                Log.i(TAG,"Exception found : ${e.message}")
            }
        }

        view.findViewById<Button>(R.id.F91_Btn_Back).setOnClickListener {
            findNavController().navigate(R.id.action_f91_Fragment_to_f90_Fragment)
        }

//        Inflate list view
        refreshResultView()

    }

    fun refreshResultView(){
        val records :List<TimePeriodRecord> = TMEPRD(activity!!.applicationContext).fetchAllRecord()
        F91_listview.adapter =null;
        for (entry in records){
            F91_listview.addView(TimePeriodRecordAdapter(activity!!.applicationContext).getView(entry))
        }
    }

    fun resetField(){
        F91_In_TMEPRD_Cd.setText("")
        F91_In_TMEPRD_Desc.setText("")

    }

}

