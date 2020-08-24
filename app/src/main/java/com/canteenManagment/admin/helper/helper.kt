package com.canteenManagment.admin.helper

import android.content.Context
import android.widget.Toast

fun showLongtoast(massage : String,mContext : Context){
    Toast.makeText(mContext,massage,Toast.LENGTH_LONG).show()
}

fun showShortToast(massage : String,mContext : Context?){
    Toast.makeText(mContext,massage,Toast.LENGTH_SHORT).show()
}