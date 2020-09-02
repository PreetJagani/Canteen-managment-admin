package com.canteenManagment.admin.MenuPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.Fragments.MenuFragment.Companion.CATEGORY_NAME
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ActivityFoodListBinding
import com.canteenmanagment.canteen_managment_library.apiManager.FirebaseApiManager
import com.canteenmanagment.canteen_managment_library.models.Food
import kotlinx.coroutines.launch

class FoodListActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding : ActivityFoodListBinding
    private val mContext : Context = this
    private lateinit var foodList: List<Food>


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

    private fun loadData(){
        scope.launch {
            FirebaseApiManager.getAllFoodFromCategory(intent.getStringExtra(CATEGORY_NAME)).let {
                binding.SRRefreshLayout.isRefreshing = false
                foodList = it

                binding.RVFoodList.adapter = FoodListRecyclerViewAdapter(it,
                            FoodListRecyclerViewAdapter.clickListner { position ->

                                val i = Intent(mContext,EditFoodActivity::class.java)
                                i.putExtra(FOOD_ITEM,foodList.get(position))
                                startActivity(i)
                            })
            }
        }
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.IMback -> onBackPressed()

            R.id.FABadd -> {
                var i = Intent(this, AddFoodActivity::class.java)
                i.putExtra(CATEGORY_NAME,intent.getStringExtra(CATEGORY_NAME))
                startActivity(i)
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top)
            }
        }
    }

    override fun onBackPressed() {
        binding.CL.visibility = View.INVISIBLE
        super.onBackPressed()

    }

    companion object{
        const val FOOD_ITEM = "Food Item"
    }


}