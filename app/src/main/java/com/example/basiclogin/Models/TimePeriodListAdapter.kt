package com.example.basiclogin.Models
//
//import android.app.AlertDialog
//import android.content.Context
//import android.content.DialogInterface
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import com.example.basiclogin.R
//import com.example.basiclogin.Tables.TMEPRD
//
////import com.google.firebase.database.FirebaseDatabase
//
//
//class TimePeriodListAdapter(val ctxt : Context, val layoutRestid : Int, val resultList :List<TimePeriodRecord>)
//    : ArrayAdapter<TimePeriodRecord>(ctxt,layoutRestid,resultList){
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val layoutinflater : LayoutInflater = LayoutInflater.from(ctxt)
//        val view : View = layoutinflater.inflate(layoutRestid,null)
//
//        val entry_code = view.findViewById<TextView>(R.id.Entry_F91_V_code)
//        val entry_Desc = view.findViewById<TextView>(R.id.Entry_F91_V_desc)
//        val entry_Btn_Update = view.findViewById<Button>(R.id.Entry_F91_Btn_Update)
//        val entry_Btn_Delete = view.findViewById<Button>(R.id.Entry_F91_Btn_Delete)
//
//        val currentResult = resultList[position]
//
//        entry_code.text = currentResult.code
//        entry_Desc.text = currentResult.desc
//
//        entry_Btn_Update.setOnClickListener {
//            showUpdateDialog(currentResult)
//        }
//
//        entry_Btn_Delete.setOnClickListener {
//            showDeleteDialog(currentResult)
//        }
//
//        return view
//    }
//
//
//
//
//}