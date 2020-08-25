package com.canteenManagment.admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.Fragments.MenuFragment.Companion.CATEGORY_NAME
import com.canteenManagment.admin.databinding.ActivityFoodListBinding

class FoodListActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding : ActivityFoodListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_food_list)
        binding.title = intent.getStringExtra(CATEGORY_NAME)

        binding.IMback.setOnClickListener(this)

        binding.FABadd.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.IMback -> super.onBackPressed()

            R.id.FABadd -> {
                var i = Intent(this,AddFoodActivity::class.java)
                i.putExtra(CATEGORY_NAME,intent.getStringExtra(CATEGORY_NAME))
                startActivity(i)
                overridePendingTransition(R.anim.slide_in_bottom,android.R.anim.fade_out)
            }



        }

    }

}