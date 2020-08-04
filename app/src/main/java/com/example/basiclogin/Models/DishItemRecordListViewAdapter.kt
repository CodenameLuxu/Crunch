package com.example.basiclogin.Models

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.*
import android.widget.*
import com.example.basiclogin.R
import com.example.basiclogin.Tables.CRUCAT
import com.example.basiclogin.Tables.DSHITM

class DishItemRecordListViewAdapter(context: Context, var resource:Int, var items:List<DishItemRecord>)
    : ArrayAdapter<DishItemRecord>( context , resource , items ){

    val TAG = "Code Desc adapter"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutinflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutinflater.inflate(R.layout.entry_f931_dshitm,null)

        val entry_cuisine = view.findViewById<TextView>(R.id.ent_F931_V_Cuisine)
        val entry_dish = view.findViewById<TextView>(R.id.ent_F931_V_Dish)


        val entry_Btn_Update = view.findViewById<Button>(R.id.ent_F931_Btn_Update)
        val entry_Btn_Delete = view.findViewById<Button>(R.id.ent_F931_Btn_Delete)

        var record : DishItemRecord = items[position]
        val cuisinecat: CuisineCategoryRecord = CRUCAT(context).getRecordByID(record.crucat)

        entry_cuisine.text = cuisinecat.desc
        entry_dish.text = record.name

        entry_Btn_Update.setOnClickListener {
            showUpdateDialog(record)
        }

        entry_Btn_Delete.setOnClickListener {
            showDeleteDialog(record)
        }

        return view
    }

    fun showUpdateDialog(updateEntry : DishItemRecord){
        try {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Update Record")

            val inflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.dialog_update_dshitm, null)

            val cuicat = view.findViewById<Spinner>(R.id.DIA_UP_DSHITM_V_Cuisine)
            val dishname = view.findViewById<EditText>(R.id.DIA_UP_DSHITM_V_Dish)
            val veganopt = view.findViewById<CheckBox>(R.id.DIA_UP_DSHITM_V_VeganOpt)
            val nutfreopt = view.findViewById<CheckBox>(R.id.DIA_UP_DSHITM_V_NutFreeOpt)
            val milkfreopt = view.findViewById<CheckBox>(R.id.DIA_UP_DSHITM_V_MilkFreOpt)
            val spicyopt = view.findViewById<CheckBox>(R.id.DIA_UP_DSHITM_V_SpicyOpt)


//            cuisine cat Spinner
            val cuisines : List<CuisineCategoryRecord> = CRUCAT(context).fetchAllRecord()
            val currentCuisine = CRUCAT(context).getRecordByID(updateEntry.crucat)
            val spinnerAdapter:CuisineCategoryRecordSpinnerAdapter = CuisineCategoryRecordSpinnerAdapter(context,R.layout.entry_spinner_string,cuisines)
            cuicat.adapter = spinnerAdapter
            cuicat.setSelection(spinnerAdapter.getPosition(currentCuisine))
//            Dish Name
            dishname.setText(updateEntry.name)
            veganopt.isChecked = updateEntry.veganOpt
            nutfreopt.isChecked = updateEntry.nutFreeOpt
            milkfreopt.isChecked = updateEntry.milkFreeOpt
            spicyopt.isChecked = updateEntry.spicyOpt


            builder.setView(view)
            builder.setPositiveButton("Update", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if (updateDialogInputCheck(view)) {
                        updateEntry.crucat = (cuicat.selectedItem as CuisineCategoryRecord).id
                        updateEntry.name = dishname.text.toString()
                        updateEntry.veganOpt = veganopt.isChecked
                        updateEntry.nutFreeOpt = nutfreopt.isChecked
                        updateEntry.milkFreeOpt = milkfreopt.isChecked
                        updateEntry.spicyOpt = spicyopt.isChecked

                        DSHITM(context).updateRecord(updateEntry)
                        refreshitems()
                    }
                }
            })
            builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    return
                }
            })

            val alert = builder.create()
            val window : Window = alert.window!!
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
            alert.show()
        }catch( e : Exception){
            Log.i(TAG,"Exception found : ${e.message}'")
        }
    }

    fun showDeleteDialog(deleteRecord :  DishItemRecord){
        try {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete record")

            val inflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.dialog_delete, null)
            builder.setView(view)

            builder.setPositiveButton("Delete", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    DSHITM(context).deleteRecord(deleteRecord)
                    refreshitems()
                }
            })
            builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    return
                }
            })

            val alert = builder.create()
            alert.show()
        }catch (e : Exception){
            Log.i(TAG, "Exception found :  ${e.message}")

        }
    }

    private fun updateDialogInputCheck(view : View): Boolean{
        val newname =  view.findViewById<EditText>(R.id.DIA_UP_DSHITM_V_Dish)
        if (newname.text.isEmpty()){
            newname.error = "Name must not be empty"
            newname.requestFocus()
            return false
        }else{
            return true
        }

    }

    fun refreshitems(){
        items  = DSHITM(context).fetchAllRecord()

    }
}