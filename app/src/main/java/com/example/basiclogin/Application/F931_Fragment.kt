package com.example.basiclogin.Application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.Models.DishItemRecord
import com.example.basiclogin.Models.DishItemRecordListViewAdapter
import com.example.basiclogin.R
import com.example.basiclogin.Tables.DSHITM
import kotlinx.android.synthetic.main.fragment_f931_frame.*

class F931_Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f931_frame, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshScrollView()

        F931_Btn_Back.setOnClickListener {
            findNavController().navigate(R.id.action_f931_Fragment_to_f93_Fragment)
        }

        F931_Btn_Refresh.setOnClickListener {
            refreshScrollView()
        }
    }

    private fun refreshScrollView(){
        val records : List<DishItemRecord> = DSHITM(context!!).fetchAllRecord()
        val listadapter = DishItemRecordListViewAdapter(context!!,R.layout.entry_f931_dshitm,records)
        F931_ScrollPane.adapter = listadapter
    }
}