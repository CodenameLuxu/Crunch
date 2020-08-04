package com.example.basiclogin.Application

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.Models.TimePeriodRecord
import com.example.basiclogin.Models.TimePeriodRecordAdapter
import com.example.basiclogin.R
import com.example.basiclogin.Tables.TMEPRD
import com.example.basiclogin.Tables.TableConstants
import kotlinx.android.synthetic.main.fragment_f91__tbl_codedesc.*



class F91_Fragment : Fragment() {
    private val TAG = "F91 Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f91__tbl_codedesc, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshResultView()

        view.findViewById<TextView>(R.id.F91_TBL_Title).setText(TableConstants.TBL_TMEPRD)

        view.findViewById<Button>(R.id.F91_Btn_Add).setOnClickListener {
            try {
                if (F91_In_TMEPRD_Cd.text.isEmpty() || F91_In_TMEPRD_Desc.text.isEmpty()) {
                    Toast.makeText(activity, "All fields must not be empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
//                    Log.i(TAG, "Attempting to add record")
                    val toAdd = TimePeriodRecord(
                        0,
                        F91_In_TMEPRD_Cd.text.toString(),
                        F91_In_TMEPRD_Desc.text.toString()
                    )
                    TMEPRD(activity!!.applicationContext).addRecord(toAdd)
                }
//                Log.i(TAG, "Adding operation finished.")
                Toast.makeText(context,"Record added",Toast.LENGTH_LONG).show()
                resetField()
                refreshResultView()
            }catch(e: Exception){
                Toast.makeText(context,"Something gone wrong",Toast.LENGTH_LONG).show()
                Log.i(TAG,"Exception found : ${e.message}")
            }

        }

        view.findViewById<Button>(R.id.F91_Btn_Refresh).setOnClickListener {
            refreshResultView()
        }

        view.findViewById<Button>(R.id.F91_Btn_Back).setOnClickListener {
            findNavController().navigate(R.id.action_f91_Fragment_to_f90_Fragment)
        }

//        Inflate list view

    }

    fun refreshResultView(){
        val records :List<TimePeriodRecord> = TMEPRD(activity!!.applicationContext).fetchAllRecord()

        val adapter = TimePeriodRecordAdapter(context!!,R.layout.entry_f91_timeperiod,records)
        F91_listview.adapter  =  adapter

//        Log.i(TAG, "F91 : Count in record : ${records.size}")
        Log.i(TAG, "F91 : Count in view : ${F91_listview.count}")
    }

    fun resetField(){
        F91_In_TMEPRD_Cd.setText("")
        F91_In_TMEPRD_Desc.setText("")

    }

}

