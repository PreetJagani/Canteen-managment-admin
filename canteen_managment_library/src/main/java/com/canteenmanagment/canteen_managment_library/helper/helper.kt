package com.canteenmanagment.canteen_managment_library.helper

import android.content.Context
import android.widget.Toast

fun showLongtoast(massage : String,mContext : Context){
    Toast.makeText(mContext,massage,Toast.LENGTH_LONG).show()
}

fun showShortToast(massage : String,mContext : Context?){
    Toast.makeText(mContext,massage,Toast.LENGTH_SHORT).show()
}