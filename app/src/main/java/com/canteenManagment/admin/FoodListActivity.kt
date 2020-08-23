package com.canteenManagment.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.canteenManagment.admin.BaseActivity.BaseActivity

class FoodListActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
    }

}