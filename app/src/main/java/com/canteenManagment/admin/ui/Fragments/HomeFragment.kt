package com.canteenManagment.admin.ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canteenManagment.admin.R
import com.canteenmanagment.canteen_managment_library.apiManager.FirebaseApiManager
import com.canteenmanagment.canteen_managment_library.models.Food
import com.canteenmanagment.canteen_managment_library.models.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*scope.launch {
            FirebaseApiManager.getAllPastFoods().let {
                *//*if(it.isSuccess)
                    Log.d("Fav",it.data.toString())*//*

            }

            //Food(id = "123",name = "test",price = 1,imageUrl = "asd",category = "cat1",available = true,availableTimes = listOf<String>(),counterNumber = 1)

        }*/


        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}