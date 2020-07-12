package com.example.basiclogin.Models

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.basiclogin.R
import com.example.basiclogin.Tables.TMEPRD

class TimePeriodRecordAdapter(val context: Context) {

     fun getView(record: TimePeriodRecord): View {
        val layoutinflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutinflater.inflate(R.layout.entry_f91_timeperiod,null)

        val entry_code = view.findViewById<TextView>(R.id.Entry_F91_V_code)
        val entry_Desc = view.findViewById<TextView>(R.id.Entry_F91_V_desc)
        val entry_Btn_Update = view.findViewById<Button>(R.id.Entry_F91_Btn_Update)
        val entry_Btn_Delete = view.findViewById<Button>(R.id.Entry_F91_Btn_Delete)

        entry_code.text = record.code
        entry_Desc.text = record.desc

        entry_Btn_Update.setOnClickListener {
            showUpdateDialog(record)
        }

        entry_Btn_Delete.setOnClickListener {
            showDeleteDialog(record)
        }

        return view
    }

    fun showUpdateDialog(updateEntry : TimePeriodRecord){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Update Record")

        val inflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.dialog_update_tmrprd,null)

        val newcode = view.findViewById<EditText>(R.id.DIA_UP_TMEPR_V_Code)
        val newDesc = view.findViewById<EditText>(R.id.DIA_UP_TMEPR_V_Desc)

        newcode.setText(updateEntry.code)
        newDesc.setText(updateEntry.desc)

        builder.setView(view)
        builder.setPositiveButton("Update" ,object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (newcode.text.isEmpty()){
                    newcode.error = "Code must not be empty"
                    newcode.requestFocus()
                }
                else if (newDesc.text.isEmpty() ){
                    newDesc.error = "Description must not be empty"
                    newDesc.requestFocus()
                }else{
                    TMEPRD(context).updateRecord(updateEntry)
                }
            }
        })
        builder.setNegativeButton("Cancel" , object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                return
            }
        })

        val alert = builder.create()
        alert.show()
    }

    fun showDeleteDialog(deleteRecord :  TimePeriodRecord){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete record")

        val inflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.dialog_delete,null)
        builder.setView(view)

        builder.setPositiveButton("Delete" ,object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                TMEPRD(context).deleteRecord(deleteRecord)
            }
        })
        builder.setNegativeButton("Cancel" , object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                return
            }
        })

        val alert = builder.create()
        alert.show()
    }
}