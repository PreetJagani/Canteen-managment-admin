package com.canteenManagment.admin.BaseActivity

import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.canteenManagment.admin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class BaseActivity : AppCompatActivity() {

    val scope = CoroutineScope(Dispatchers.Main)

}