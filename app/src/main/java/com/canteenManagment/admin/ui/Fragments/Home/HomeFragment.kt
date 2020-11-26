package com.canteenManagment.admin.ui.Fragments.Home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.FragmentHomeBinding
import com.canteenManagment.admin.databinding.FragmentMenuBinding
import com.canteenManagment.admin.helper.CustomProgressBar
import com.canteenManagment.admin.ui.ScanActivity
import com.canteenmanagment.canteen_managment_library.apiManager.FirebaseApiManager
import com.canteenmanagment.canteen_managment_library.models.Food
import com.canteenmanagment.canteen_managment_library.models.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var orderList : MutableList<Order>
    private lateinit var progressDialog: CustomProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.RVPrepareFoodList

        progressDialog = CustomProgressBar(activity as Activity)
        loadData()
        //test()

        binding.IMScan.setOnClickListener {
            val intent = Intent(context,ScanActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }

    private fun loadData(){
        progressDialog.startDialog()
        scope.launch {
            FirebaseApiManager.getAllInProgressOrder().let {
                progressDialog.stopDiaolog()
                if(it.isSuccess){
                    orderList = it.data as MutableList<Order>
                    binding?.RVPrepareFoodList?.adapter = PrepareOrderListRecyclerViewAdapter(orderList as List<Order>){ order -> makeOrderReady(order) }
                }
            }
        }
    }

    private fun makeOrderReady(order: Order){
        progressDialog.startDialog()
        scope.launch {
            FirebaseApiManager.makeOrderReady(order).let {
                progressDialog.stopDiaolog()
                if(it.isSuccess){
                    orderList.remove(order)
                    binding.RVPrepareFoodList.adapter = PrepareOrderListRecyclerViewAdapter(orderList as List<Order>){ order -> makeOrderReady(order) }
                }
            }
        }

    }

    private fun test(){
        scope.launch {
            FirebaseApiManager.getRecommendedFood("1H3uTXfutCgxsnsgBIW4")
                .let {
                if(it.isSuccess)
                    Log.d("Fav",(it.data as List<Food>).toString())
            }

            //Food(id = "123",name = "test",price = 1,imageUrl = "asd",category = "cat1",available = true,availableTimes = listOf<String>(),counterNumber = 1)

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}