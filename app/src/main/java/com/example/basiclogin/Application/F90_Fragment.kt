package com.example.basiclogin.Application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.R

class F90_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f90_optionhome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scrollplane = view.findViewById<ScrollView>(R.id.F90_scrollview)
        scrollplane.removeAllViews()

        val contentview = layoutInflater.inflate(R.layout.fragment_f90_content,null)
        scrollplane.addView(contentview)

        contentview.findViewById<Button>(R.id.F90_Btn_TimePeriod).setOnClickListener {
            findNavController().navigate(R.id.action_f90_Fragment_to_f91_Fragment)
        }

        contentview.findViewById<Button>(R.id.F90_Btn_Back).setOnClickListener {
            findNavController().navigate(R.id.action_f90_Fragment_to_f01_Fragment)
        }

        contentview.findViewById<Button>(R.id.F90_Btn_Crusine).setOnClickListener {
            findNavController().navigate(R.id.action_f90_Fragment_to_f92_Fragment)
        }
        contentview.findViewById<Button>(R.id.F90_Btn_DishItem).setOnClickListener {
            findNavController().navigate(R.id.action_f90_Fragment_to_f93_Fragment)
        }

    }

}