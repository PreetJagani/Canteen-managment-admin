package com.canteenManagment.admin.ui.FoodDetail.listFood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ActivityFoodListBinding
import com.canteenManagment.admin.helper.CustomProgressBar
import com.canteenManagment.admin.helper.showShortToast
import com.canteenManagment.admin.ui.FoodDetail.addFood.AddFoodActivity
import com.canteenManagment.admin.ui.FoodDetail.editFood.EditFoodActivity
import com.canteenManagment.admin.ui.Fragments.MenuFragment.Companion.CATEGORY_NAME
import com.canteenmanagment.canteen_managment_library.apiManager.FirebaseApiManager
import com.canteenmanagment.canteen_managment_library.models.Food
import kotlinx.coroutines.launch

class FoodListActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityFoodListBinding
    private val mContext: Context = this
    private lateinit var foodList: List<Food>
    private val progressDialog: CustomProgressBar = CustomProgressBar(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_food_list
        )
        binding.title = intent.getStringExtra(CATEGORY_NAME)

        binding.IMback.setOnClickListener(this)

        binding.FABadd.setOnClickListener(this)

        loadData()

        //binding.SRRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING)
        binding.SRRefreshLayout.setOnRefreshListener {
            loadData()
        }


    }

    private fun loadData() {
        scope.launch {
            FirebaseApiManager.getAllFoodFromCategory(intent.getStringExtra(CATEGORY_NAME)).let {
                binding.SRRefreshLayout.isRefreshing = false
                foodList = it
                binding.RVFoodList.visibility = View.VISIBLE
                binding.RVFoodList.adapter = FoodListRecyclerViewAdapter(it,
                    FoodListRecyclerViewAdapter.ClickListner(
                        { position ->
                            val i = Intent(mContext, EditFoodActivity::class.java)
                            i.putExtra(FOOD_ITEM, foodList.get(position))
                            startActivityForResult(i, DATA_CHANGE)
                        },
                        { position,status ->
                            var food = foodList.get(position)
                            food.available = status
                            updateFood(food)
                        })
                )
            }
        }
    }

    private fun updateFood(food: Food) {

        progressDialog.startDialog()

        scope.launch {

            FirebaseApiManager.updateFoodData(food).let {
                progressDialog.stopDiaolog()
                when (it.isSuccess) {
                    true -> {
                        //showShortToast(it.message, mContext)
                    }

                    false -> showShortToast(it.message, mContext)
                }
            }

        }


    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.IMback -> onBackPressed()

            R.id.FABadd -> {
                var i = Intent(this, AddFoodActivity::class.java)
                i.putExtra(CATEGORY_NAME, intent.getStringExtra(CATEGORY_NAME))
                startActivityForResult(i, DATA_CHANGE)
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
            }
        }
    }

    override fun onBackPressed() {
        binding.CL.visibility = View.INVISIBLE
        super.onBackPressed()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DATA_CHANGE) {
            binding.RVFoodList.visibility = View.GONE
            loadData()
        }
    }

    companion object {
        const val FOOD_ITEM = "Food Item"
        const val DATA_CHANGE = 2002

    }


}