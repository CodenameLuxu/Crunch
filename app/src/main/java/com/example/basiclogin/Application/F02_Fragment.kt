package com.example.basiclogin.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import 	android.util.Log
import android.app.DatePickerDialog
import android.widget.TextView
import java.util.Calendar
import 	java.text.SimpleDateFormat
import com.example.basiclogin.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 *  food logging functionality
 */
class F02_Fragment : Fragment() {
    private val TAG = "F02 Fragment"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_f02_log, container, false)


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.F02_BTN_Home).setOnClickListener {
            findNavController().navigate(R.id.action_f02_Fragment_to_f01_Fragment)
        }

        var inputfield = view.findViewById<ScrollView>(R.id.F02_ScrollPane);
        inputfield.removeAllViews()

//       Prep input fields functionality
        var inputview =  layoutInflater.inflate(R.layout.fragment_f02_inputfields,null)
        initInputFunctions(inputview)

        inputfield.addView(inputview)
    }

     private fun initInputFunctions(view : View){
//       Date Picker
        val cal = Calendar.getInstance()
        val input_date = view.findViewById<TextView>(R.id.F02_input_Date)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, setyear, setmonth, setday ->
                val setmonth = setmonth + 1
                val datestr = "$setday/$setmonth/$setyear"
                input_date.text = datestr
            }

        view.findViewById<Button>(R.id.F02_Btn_Calendar).setOnClickListener {
            DatePickerDialog(
                context!!, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        Log.d(TAG,"Functionality Initialized")

    }
}