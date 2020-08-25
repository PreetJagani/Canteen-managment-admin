package com.canteenManagment.admin.food

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.Fragments.MenuFragment.Companion.CATEGORY_NAME
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityAddFoodBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_add_food
        )
        setContentView(binding.root)

        binding.IMback.setOnClickListener(this)
        binding.TVtitle.text = "Add ${intent.getStringExtra(CATEGORY_NAME)}"



        binding.SPcounterNumber.adapter = CustomeSpinnerAdapter(this,listOf(1,2,3,4,5))


    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.IMback ->{
                super.onBackPressed()
                overridePendingTransition(android.R.anim.fade_in,
                    R.anim.slide_out_bottom
                )
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_top,
            R.anim.slide_out_bottom
        )
    }
}