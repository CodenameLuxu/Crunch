package com.example.basiclogin.Application

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.Models.CuisineCategoryRecord
import com.example.basiclogin.Models.CuisineCategoryRecordAdapter
import com.example.basiclogin.Models.DishItemRecord
import com.example.basiclogin.R
import com.example.basiclogin.Tables.DSHITM
import com.example.basiclogin.Tables.TableConstants
import kotlinx.android.synthetic.main.fragment_f93_dshitm.*

class F93_Fragment : Fragment() {
    private val TAG = "F93 Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f93_dshitm, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.F92_TBL_Title).setText(TableConstants.TBL_DSHITM)

        view.findViewById<Button>(R.id.F92_Btn_Submit).setOnClickListener {
            try {
                if (!requireFieldCheck()){
                    throw Exception("Required field is blank")
                }
                addRecord()
                Toast.makeText(context,"Record Added", Toast.LENGTH_LONG).show()
                resetField()

            }catch(e: Exception){
                Toast.makeText(context,"${e.message}", Toast.LENGTH_LONG).show()
                Log.i(TAG,"Exception found : ${e.message}")
            }

        }

        view.findViewById<Button>(R.id.F92_Btn_Clear).setOnClickListener {
            resetField()
        }

        view.findViewById<Button>(R.id.F92_Btn_Back).setOnClickListener {
            findNavController().navigate(R.id.action_f92_Fragment_to_f90_Fragment)
        }

    }

    fun addRecord(){
        val crusine: CuisineCategoryRecord = F92_In_CruCat.selectedItem as CuisineCategoryRecord
        val dishname = F92_In_DishName.text.toString()
        val veganOpt = F92_In_Vegan.isChecked
        val nutfreeopt = F92_In_NutFree.isChecked
        val milkfreeopt = F92_In_MilkFree.isChecked
        val spicyopt = F92_In_Spicy.isChecked

        val record : DishItemRecord = DishItemRecord(0,"",dishname,crusine.id,veganOpt,nutfreeopt,milkfreeopt,spicyopt)
        DSHITM(context!!).addRecord(record)

    }

    fun requireFieldCheck():Boolean{
        return F92_In_DishName.text.isBlank()
    }

    fun resetField(){
        F92_In_CruCat.setSelection(0)
        F92_In_DishName.setText("")
        F92_In_Vegan.isChecked = false
        F92_In_NutFree.isChecked = false
        F92_In_MilkFree.isChecked = false
        F92_In_Spicy.isChecked = false

    }
}