package com.example.basiclogin.Models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.basiclogin.R
import com.example.basiclogin.Tables.CRUCAT


class DishItemRecordSpinnerAdapter(context: Context, var resource:Int, var items:List<DishItemRecord>)
    : ArrayAdapter<DishItemRecord>( context , resource , items ){

    val TAG = "CRUCAT spinner adapter"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val current = items[position]

        val layoutinflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutinflater.inflate(resource,null)

        val entry_desc = view.findViewById<TextView>(R.id.entry_spinner_desc)
        entry_desc.setText(current.name)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val current = items[position]

        val crusineType = CRUCAT(context).getRecordByID(current.crucat)

        val layoutinflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutinflater.inflate(resource,null)

        val entry_desc = view.findViewById<TextView>(R.id.entry_spinner_desc)
        val description = "${crusineType.desc} - ${current.name} "
        entry_desc.setText(description)
        return view
    }
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): DishItemRecord {
        return items[position]
    }
}