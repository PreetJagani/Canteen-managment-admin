package com.canteenManagment.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.Fragments.MenuFragment.Companion.CATEGORY_NAME
import com.canteenManagment.admin.databinding.ActivityAddFoodBinding
import com.canteenManagment.admin.helper.showShortToast

class AddFoodActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_food)
        setContentView(binding.root)

        binding.IMback.setOnClickListener(this)
        binding.TVtitle.text = "Add ${intent.getStringExtra(CATEGORY_NAME)}"
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.IMback ->{
                super.onBackPressed()
                overridePendingTransition(android.R.anim.fade_in,R.anim.slide_out_bottom)
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,R.anim.slide_out_bottom)
    }
}