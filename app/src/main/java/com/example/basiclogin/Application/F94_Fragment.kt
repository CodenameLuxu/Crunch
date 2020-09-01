package com.example.basiclogin.Application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.Models.DishItemRecord
import com.example.basiclogin.Models.DishItemRecordListViewAdapter
import com.example.basiclogin.Models.EatHistoryRecord
import com.example.basiclogin.Models.EatHistoryRecordListViewAdapter
import com.example.basiclogin.R
import com.example.basiclogin.Statics
import com.example.basiclogin.Tables.DSHITM
import com.example.basiclogin.Tables.EATHIST
import kotlinx.android.synthetic.main.fragment_f931_frame.*

class F94_Fragment : Fragment() {
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
            findNavController().navigate(R.id.action_f94_Fragment_to_f90_Fragment)
        }

        F931_Btn_Refresh.setOnClickListener {
            refreshScrollView()
        }

    }

    private fun refreshScrollView(){
        val userid = this.activity?.intent?.getStringExtra(Statics.KEY_USERID)?.toInt()!!
        val records : List<EatHistoryRecord> = EATHIST(context!!).fetchAllRecordByUser(userid)
        val listadapter = EatHistoryRecordListViewAdapter(context!!, R.layout.entry_f94_eathist,records)
        F931_ScrollPane.adapter = listadapter
    }
}