package com.canteenManagment.admin.food

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.Fragments.MenuFragment.Companion.CATEGORY_NAME
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ActivityAddFoodBinding
import com.canteenManagment.admin.helper.CustomProgressBar
import com.canteenmanagment.canteen_managment_library.models.Food
import kotlinx.coroutines.launch

class AddFoodActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddFoodBinding
    private val mContext : Context = this
    private var progressBar = CustomProgressBar()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_food
        )
        setContentView(binding.root)

        binding.IMback.setOnClickListener(this)
        binding.TVtitle.text = "Add ${intent.getStringExtra(CATEGORY_NAME)}"

        binding.BTadd.setOnClickListener(this)

        binding.SPcounterNumber.adapter = CustomeSpinnerAdapter(this, listOf(1, 2, 3, 4, 5))


    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.IMback -> {
                super.onBackPressed()
                overridePendingTransition(
                    android.R.anim.fade_in,
                    R.anim.slide_out_bottom
                )
            }
            R.id.BTadd -> {
                var food = Food()
                food.name = binding.ETname.text.toString().trim()
                food.price = binding.ETPrice.text.toString().trim().toInt()
                food.counterNumber = binding.SPcounterNumber.selectedItemPosition + 1
                food.category = intent.getStringExtra(CATEGORY_NAME)
                food.imageUrl = "no url"
                food.available = true

                progressBar.show(this)

                scope.launch {

                    Thread.sleep(5000)
                    progressBar.dialog.dismiss()
                    /*FirebaseApiManager.storeFoodData(food).let {
                        customDialog.dismissDiolog()
                        when(it.success){
                            true-> showShortToast(it.message,mContext)

                            false-> showShortToast(it.message,mContext)
                        }
                    }*/


                }
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_top,
            R.anim.slide_out_bottom
        )
    }


}

