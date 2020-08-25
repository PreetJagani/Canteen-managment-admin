package com.canteenManagment.admin.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.canteenManagment.admin.food.FoodListActivity
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.FragmentMenuBinding
import com.canteenmanagment.canteen_managment_library.models.food


class MenuFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("MenuFragment","menu started")
        _binding = FragmentMenuBinding.inflate(inflater,container,false)


        binding.snacks = food.Category.SNACKS.value
        binding.fixThali = food.Category.FIX_THALI.value
        binding.drinks = food.Category.DRINKS.value
        binding.punjabiMeal = food.Category.PUNJABI_MEAL.value

        binding.CL1.setOnClickListener(this)
        binding.CL2.setOnClickListener(this)
        binding.CL3.setOnClickListener(this)
        binding.CL4.setOnClickListener(this)

        return binding?.root
    }

    override fun onClick(v: View?) {
        var i = Intent(activity?.applicationContext,
            FoodListActivity::class.java)
        var options : ActivityOptionsCompat? = null
        when(v?.id){
            R.id.CL1 -> {
                i.putExtra(CATEGORY_NAME,food.Category.SNACKS.value)
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity,binding.CL1,"open_transition")
            }
            R.id.CL2 -> {
                i.putExtra(CATEGORY_NAME,food.Category.FIX_THALI.value)
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity,binding.CL2,"open_transition")
            }
            R.id.CL3 ->{
                i.putExtra(CATEGORY_NAME,food.Category.DRINKS.value)
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity,binding.CL3,"open_transition")
            }
            R.id.CL4 -> {
                i.putExtra(CATEGORY_NAME,food.Category.PUNJABI_MEAL.value)
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity,binding.CL4,"open_transition")
            }
        }


        startActivity(i,options?.toBundle())

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        const val CATEGORY_NAME = "TITLE_NAME"
    }
}