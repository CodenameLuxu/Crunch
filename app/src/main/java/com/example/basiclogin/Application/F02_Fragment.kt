package com.example.basiclogin.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import 	android.util.Log
import android.app.DatePickerDialog
import android.widget.*
import com.example.basiclogin.Models.TimePeriodRecorSpinnerdAdapter
import java.util.Calendar
import com.example.basiclogin.R
import com.example.basiclogin.Tables.TMEPRD

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
        initButtonFunctions(view)

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

         val timeperiodspinner = view.findViewById<Spinner>(R.id.F02_input_time)
         val timeprdrecords = TMEPRD(context!!).fetchAllRecord()
         val timespinneradapter = TimePeriodRecorSpinnerdAdapter(context!!,R.layout.entry_spinner_string,timeprdrecords)
         timeperiodspinner.adapter = timespinneradapter



         view.findViewById<Button>(R.id.F02_BTN_Home)
        Log.d(TAG,"Functionality Initialized")

    }

    private fun initButtonFunctions(view : View) {
        val btn_home = view.findViewById<Button>(R.id.F02_BTN_Home)
        val btn_clear = view.findViewById<Button>(R.id.F02_BTN_Clear)
        val btn_submit = view.findViewById<Button>(R.id.F02_BTN_Submit)

        btn_clear.setOnClickListener {
            clearinput(view.findViewById(R.id.F02_ScrollPane))
        }

        btn_home.setOnClickListener {
            findNavController().navigate(R.id.action_f02_Fragment_to_f01_Fragment)
        }

        btn_submit.setOnClickListener{
            Log.i(TAG, " Submit is clicked")
        }




    }
    private fun clearinput(view: View){
        view.findViewById<TextView>(R.id.F02_input_Date).setText("")
        view.findViewById<Spinner>(R.id.F02_input_time).setSelection(0)
        view.findViewById<Spinner>(R.id.F02_Input_dish).setSelection(0)


    }
}