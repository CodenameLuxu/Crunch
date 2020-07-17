package com.example.basiclogin.Service

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.basiclogin.ui.login.RegisterActivty

class UIService(val context: Activity) {

    fun ideKeyboard(view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow( view.windowToken, 0)
    }
}