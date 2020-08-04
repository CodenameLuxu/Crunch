package com.example.basiclogin.Models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.basiclogin.R

class CuisineCategoryRecordSpinnerAdapter(context: Context, var resource:Int, var items:List<CuisineCategoryRecord>)
    : ArrayAdapter<CuisineCategoryRecord>( context , resource , items ){

    val TAG = "CRUCAT spinner adapter"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutinflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutinflater.inflate(resource,null)

        val entry_desc = view.findViewById<TextView>(R.id.entry_spinner_desc)
        entry_desc.setText(items[position].desc)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getView(position,convertView,parent!!)
    }
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): CuisineCategoryRecord {
        return items[position]
    }
}


