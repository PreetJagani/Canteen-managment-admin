package com.canteenManagment.admin.helper

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import com.canteenManagment.admin.R
import kotlinx.android.synthetic.main.progress_bar.view.*
import androidx.core.graphics.*
import kotlinx.android.synthetic.main.delete_custome_diolog.view.*
import androidx.core.graphics.drawable.toDrawable as toDrawable

class DeleteCustomDiolog(val activity : Activity) {

    lateinit var alertDialog : Dialog

    fun startDialog(deleteFood : () -> Unit){

        var dialog = AlertDialog.Builder(activity)

        var view = LayoutInflater.from(activity).inflate(R.layout.delete_custome_diolog,null)

        view.BT_cancel.setOnClickListener {
            alertDialog.dismiss()
        }
        view.BT_delete.setOnClickListener {
            deleteFood()
        }

        dialog.setView(view)

        alertDialog = dialog.create()
        alertDialog.show()
        alertDialog.setCancelable(true)
        alertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)

    }
    fun stopDiaolog(){
        alertDialog.dismiss()
    }
}