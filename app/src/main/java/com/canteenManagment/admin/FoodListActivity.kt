package com.canteenManagment.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.Fragments.MenuFragment.Companion.TITLE_NAME
import com.canteenManagment.admin.databinding.ActivityFoodListBinding

class FoodListActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding : ActivityFoodListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_food_list)

        binding.IMback.setOnClickListener(this)

        binding.title = intent.getStringExtra(TITLE_NAME)

    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.IMback ->{
                super.onBackPressed()
            }


        }

    }

}