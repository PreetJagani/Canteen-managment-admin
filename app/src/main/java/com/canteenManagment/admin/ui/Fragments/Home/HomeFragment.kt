package com.canteenManagment.admin.ui.Fragments.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.FragmentHomeBinding
import com.canteenManagment.admin.databinding.FragmentMenuBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.RVPrepareFoodList

        loadData()


        return binding.root
    }

    private fun loadData(){
        scope.launch {
            FirebaseApiManager.getAllInProgressOrder().let {
                if(it.isSuccess)
                    binding.RVPrepareFoodList.adapter = PrepareOrderListRecyclerViewAdapter(it.data as List<Order>)

            }
        }
    }

    private fun test(){
        /*scope.launch {
            FirebaseApiManager.test("Afternoon").let {
                if(it.isSuccess)
                    Log.d("Fav",it.data.toString())
            }

            //Food(id = "123",name = "test",price = 1,imageUrl = "asd",category = "cat1",available = true,availableTimes = listOf<String>(),counterNumber = 1)

        }*/
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}