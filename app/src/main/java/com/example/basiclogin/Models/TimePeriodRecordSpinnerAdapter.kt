package com.example.basiclogin.Models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.basiclogin.R

class TimePeriodRecorSpinnerdAdapter(context: Context, var resource:Int, var items:List<TimePeriodRecord>)
    : ArrayAdapter<TimePeriodRecord>( context , resource , items ){

    val TAG = "Time Period spinner adapter"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val current = items[position]

        val layoutinflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutinflater.inflate(R.layout.entry_spinner_string,null)

        val entry_desc = view.findViewById<TextView>(R.id.entry_spinner_desc)
        var record : TimePeriodRecord = items[position]

        entry_desc.text = record.desc

        return view

    }
}