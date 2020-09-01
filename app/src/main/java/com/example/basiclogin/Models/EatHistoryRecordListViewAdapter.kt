package com.example.basiclogin.Models

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.basiclogin.Application.F94_Fragment
import com.example.basiclogin.R
import com.example.basiclogin.Tables.DSHITM
import com.example.basiclogin.Tables.EATHIST
import com.example.basiclogin.Tables.FormatConstants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EatHistoryRecordListViewAdapter(
    context: Context,
    var resource: Int,
    var items: List<EatHistoryRecord>
): ArrayAdapter<EatHistoryRecord>(context, resource, items){

    val TAG = "F94 Adapter"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutinflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutinflater.inflate(R.layout.entry_f94_eathist, null)

        val entry_Date = view.findViewById<TextView>(R.id.ent_f94_L_Date)
        val entry_dish = view.findViewById<TextView>(R.id.ent_f94_L_food)
        val entry_Btn_Delete = view.findViewById<Button>(R.id.ent_f94_Btn_Delete)

        var record : EatHistoryRecord = items[position]
//        val cuisinecat: CuisineCategoryRecord = CRUCAT(context).getRecordByID(record.crucat)
        val dish = DSHITM(context).getRecordByID(record.dishid)

        val date = LocalDate.parse(
            record.date,
            DateTimeFormatter.ofPattern(FormatConstants.F_Date_Store)
        ).format(DateTimeFormatter.ofPattern(FormatConstants.F_Date_Display))
        entry_Date.text = date
        entry_dish.text = dish.name

        entry_Btn_Delete.setOnClickListener {
            showDeleteDialog(record)
        }

        return view
    }


    fun showDeleteDialog(deleteRecord: EatHistoryRecord){
        try {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete record")

            val inflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.dialog_delete, null)
            builder.setView(view)

            builder.setPositiveButton("Delete", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    EATHIST(context).deleteRecord(deleteRecord)
                    refreshitems()
                }
            })
            builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    return
                }
            })

            val alert = builder.create()
            alert.show()
        }catch (e: Exception){
            Log.i(TAG, "Exception found :  ${e.message}")

        }
    }

    fun refreshitems(){
        items  = EATHIST(context).fetchAllRecord()
    }
}