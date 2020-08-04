package com.example.basiclogin.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.basiclogin.Models.DishItemRecord
import com.example.basiclogin.R
import com.example.basiclogin.Statics
import com.example.basiclogin.Tables.EATHIST

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class F03_Fragment : Fragment() {
    val TAG = "F03 fragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f03_picker, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userid =  this.activity?.intent?.getStringExtra(Statics.KEY_USERID)?.toInt()
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.F03_Btn_Home).setOnClickListener {
            findNavController().navigate(R.id.action_f03_Fragment_to_f01_Fragment)
        }

        view.findViewById<Button>(R.id.f03_Btn_Pick).setOnClickListener {
            try {
                val pick = pickFood(userid!!)
                Log.i(TAG, "picked : ${pick.name}")
                view.findViewById<TextView>(R.id.F03_result_name).setText(pick.name)
            }catch (e : Exception){
                Log.i(TAG, "Exception found ${e.message}")
                e.printStackTrace()
                Toast.makeText(context,"Can't pick!", Toast.LENGTH_LONG).show()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun pickFood(userid: Int):DishItemRecord{
        return EATHIST(context!!).getRecommendation(userid)
    }
}