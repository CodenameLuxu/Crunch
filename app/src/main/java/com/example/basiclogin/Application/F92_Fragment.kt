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
import com.example.basiclogin.Models.CuisineCategoryRecord
import com.example.basiclogin.Models.CuisineCategoryRecordAdapter
import com.example.basiclogin.R
import com.example.basiclogin.Tables.CRUCAT
import com.example.basiclogin.Tables.TableConstants
import kotlinx.android.synthetic.main.fragment_f91__tbl_codedesc.*

class F92_Fragment : Fragment() {
//    Cusine Category management - codedesc
    private val TAG = "F92 Fragment"

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

        view.findViewById<TextView>(R.id.F91_TBL_Title).setText(TableConstants.TBL_CRUCAT)

        view.findViewById<Button>(R.id.F91_Btn_Add).setOnClickListener {
            try {
                if (requireFieldCheck()){
                    throw Exception("Required field is blank")
                }
                addRecord()

            }catch(e: Exception){
                Toast.makeText(context,"${e.message}", Toast.LENGTH_LONG).show()
                Log.i(TAG,"Exception found : ${e.message}")
            }

        }

        view.findViewById<Button>(R.id.F91_Btn_Refresh).setOnClickListener {
            resetField()
        }

        view.findViewById<Button>(R.id.F91_Btn_Back).setOnClickListener {
            findNavController().navigate(R.id.action_f91_Fragment_to_f90_Fragment)
        }

    }

    fun addRecord(){
        val code =  F91_In_TMEPRD_Cd.text.toString()
        val desc  = F91_In_TMEPRD_Desc.toString()

        val record : CuisineCategoryRecord = CuisineCategoryRecord(0,code,desc)
        CRUCAT(context!!).addRecord(record)

    }

    fun requireFieldCheck():Boolean{
        return F91_In_TMEPRD_Cd.text.isEmpty() or F91_In_TMEPRD_Desc.text.isEmpty()
    }

    fun resetField(){
        F91_In_TMEPRD_Cd.setText("")
        F91_In_TMEPRD_Desc.setText("")
    }

    fun refreshList(){
        val records : List<CuisineCategoryRecord> = CRUCAT(context!!).fetchAllRecord()
        val listadapter = CuisineCategoryRecordAdapter(context!!,R.layout.entry_f91_timeperiod,records)
        F91_listview.adapter = listadapter
    }
}