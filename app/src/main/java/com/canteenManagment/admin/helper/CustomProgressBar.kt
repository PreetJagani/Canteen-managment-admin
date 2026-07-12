package com.canteenManagment.admin.helper

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import com.canteenManagment.admin.R
import androidx.core.graphics.*
import androidx.core.graphics.drawable.toDrawable as toDrawable

class CustomProgressBar(val activity : Activity) {

    lateinit var alertDialog : Dialog

    fun startDialog(){

        var dialog = AlertDialog.Builder(activity)

        var view = LayoutInflater.from(activity).inflate(R.layout.progress_bar,null)

        dialog.setView(view)

        alertDialog = dialog.create()
        alertDialog.show()
        alertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)

    }
    fun stopDiaolog(){
        alertDialog.dismiss()
    }
}