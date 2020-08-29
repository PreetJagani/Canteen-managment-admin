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
import androidx.core.graphics.drawable.toDrawable as toDrawable

class CustomProgressBar(val activity : Activity) {

    lateinit var alertDialog : Dialog

    fun startDialog(){

        var dialog = AlertDialog.Builder(activity)

        var view = LayoutInflater.from(activity).inflate(R.layout.progress_bar,null)

        //view.lottieAnimation.playAnimation()

       /* val layoutBuilder = LayoutInflater.from(activity).inflate(R.layout.progress_bar, null)
        val builder:AlertDialog.Builder = AlertDialog.Builder(activity).setView(layoutBuilder)
        val alertDialog:AlertDialog = builder.show()

        layoutBuilder.lottieAnimation.playAnimation()*/

        dialog.setView(view)

        alertDialog = dialog.create()
        //alertDialog.setContentView(R.layout.progress_bar)
        alertDialog.show()

    }
    fun stopDiaolog(){
        alertDialog.dismiss()
    }
}