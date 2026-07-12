package com.canteenManagment.admin.helper

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun showLongToast(massage : String,mContext : Context){
    Toast.makeText(mContext,massage,Toast.LENGTH_LONG).show()
}

fun showShortToast(massage : String,mContext : Context?){
    Toast.makeText(mContext,massage,Toast.LENGTH_SHORT).show()
}

/**
 * Android 15+ (targetSdk 35+) draws content edge-to-edge unconditionally, so screens
 * that relied on the theme reserving space for the status/navigation bars need to pad
 * for those insets themselves.
 */
fun View.applySystemBarInsets(top: Boolean = true, bottom: Boolean = true) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.updatePadding(
            top = if (top) bars.top else v.paddingTop,
            bottom = if (bottom) bars.bottom else v.paddingBottom
        )
        insets
    }
}