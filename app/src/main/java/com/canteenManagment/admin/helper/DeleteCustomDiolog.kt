package com.canteenManagment.admin.helper

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import com.canteenManagment.admin.databinding.DeleteCustomeDiologBinding
import androidx.core.graphics.*
import androidx.core.graphics.drawable.toDrawable as toDrawable

class DeleteCustomDiolog(val activity : Activity) {

    lateinit var alertDialog : Dialog

    fun startDialog(deleteFood : () -> Unit){

        var dialog = AlertDialog.Builder(activity)

        val binding = DeleteCustomeDiologBinding.inflate(LayoutInflater.from(activity))

        binding.BTCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        binding.BTDelete.setOnClickListener {
            deleteFood()
        }

        dialog.setView(binding.root)

        alertDialog = dialog.create()
        alertDialog.show()
        alertDialog.setCancelable(true)
        alertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)

    }
    fun stopDiaolog(){
        alertDialog.dismiss()
    }
}