package com.example.basiclogin.Application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.R


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class F01_Fragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f01_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.F01_BTN_Log).setOnClickListener {
            findNavController().navigate(R.id.action_f01_Fragment_to_f02_Fragment)
        }

        view.findViewById<Button>(R.id.F01_BTN_Picker).setOnClickListener {
            findNavController().navigate(R.id.action_f01_Fragment_to_f03_Fragment)
        }

        view.findViewById<Button>(R.id.F01_Btn_Option).setOnClickListener {
            findNavController().navigate(R.id.action_f01_Fragment_to_f90_Fragment)
        }
    }

}