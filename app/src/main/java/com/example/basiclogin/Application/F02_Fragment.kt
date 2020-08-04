package com.example.basiclogin.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import 	android.util.Log
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.basiclogin.Models.*
import java.util.Calendar
import com.example.basiclogin.R
import com.example.basiclogin.Statics
import com.example.basiclogin.Tables.DSHITM
import com.example.basiclogin.Tables.EATHIST
import com.example.basiclogin.Tables.FormatConstants
import com.example.basiclogin.Tables.TMEPRD
import kotlinx.android.synthetic.main.fragment_f02_inputfields.*
import kotlinx.android.synthetic.main.fragment_f02_log.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.F02_BTN_Home).setOnClickListener {
            findNavController().navigate(R.id.action_f02_Fragment_to_f01_Fragment)
        }

        var inputfield = view.findViewById<ScrollView>(R.id.F02_ScrollPane);
        inputfield.removeAllViews()

//       Prep input fields functionality
        var inputview =  layoutInflater.inflate(R.layout.fragment_f02_inputfields,null)
        initInputFields(inputview)
        initButtonFunctions(view)

        inputfield.addView(inputview)
    }

    private fun initInputFields(view : View) {
//       Date Picker
        var datepicker = view.findViewById<Button>(R.id.F02_Btn_Calendar)
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, setyear, setmonth, setday ->
                val setmonth = setmonth + 1
                val datestr = "${setday.toString().padStart(2, '0')}/${setmonth.toString().padStart(2, '0')}/$setyear"
                F02_input_Date.text = datestr
            }

        datepicker.setOnClickListener {
            DatePickerDialog(
                context!!, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        // Time period

        var timePeriodspinner = view.findViewById<Spinner>(R.id.F02_input_time)
        val timeprdrecords: List<TimePeriodRecord> = TMEPRD(context!!).fetchAllRecord()
        val timespinneradapter = TimePeriodRecorSpinnerdAdapter(context!!,R.layout.entry_spinner_string,timeprdrecords)
        timePeriodspinner.adapter = timespinneradapter

        // Dish
        var dishspinner = view.findViewById<Spinner>(R.id.F02_Input_dish)
        val dishrecord = DSHITM(context!!).fetchAllRecord()
        val dishadapter = DishItemRecordSpinnerAdapter(context!!,R.layout.entry_spinner_string,dishrecord)
        dishspinner.adapter = dishadapter


    }

    @RequiresApi(Build.VERSION_CODES.O)
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
            try {
                if (isInputValid(view)) {
                    submitRecord(view)
                    clearinput(view)
                    Toast.makeText(context,"Record added", Toast.LENGTH_LONG).show()
                }
            }catch(e : Exception){
                Toast.makeText(context,"${e.message}", Toast.LENGTH_LONG).show()
                Log.i(TAG,"Exception found : ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitRecord(view: View){
        val userid =  this.activity?.intent?.getStringExtra(Statics.KEY_USERID)?.toInt()!!

        val date = F02_input_Date.text.toString()
        val time = F02_input_time.selectedItem as TimePeriodRecord
        val dish = F02_Input_dish.selectedItem as DishItemRecord

        val parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(FormatConstants.F_Date_Display)).format(DateTimeFormatter.ofPattern(FormatConstants.F_Date_Store))
        Log.i(TAG, "Parsed date : ${parsedDate}")
        val newrecord = EatHistoryRecord(0,userid,dish.id,time.id,parsedDate)
        EATHIST(context!!).addRecord(newrecord)

    }

    private fun isInputValid(view: View): Boolean{


        if (F02_input_Date.text.isEmpty()){
            throw Exception("Date is empty")
            return false
//        }else if (!F02_Input_dish.isSelected){
//            throw Exception("Dish is not selected")
//            return false
//        }else if (!F02_input_time.isSelected){
//            throw Exception("Time period not selected")
//            return false
        }else{
            return true
        }

    }


    private fun clearinput(view: View){
        view.findViewById<TextView>(R.id.F02_input_Date).setText("")
        view.findViewById<Spinner>(R.id.F02_input_time).setSelection(0)
        view.findViewById<Spinner>(R.id.F02_Input_dish).setSelection(0)
    }


}