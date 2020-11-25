package com.canteenManagment.admin.ui.Fragments.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ItemFoodListLayoutBinding
import com.canteenManagment.admin.databinding.ItemPreparingFoodListLayoutBinding
import com.canteenmanagment.canteen_managment_library.models.Food
import com.canteenmanagment.canteen_managment_library.models.Order

class PrepareOrderListRecyclerViewAdapter(val orderList : List<Order>, val makeOrderReady : (order : Order)-> Unit) : RecyclerView.Adapter<PrepareOrderListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPreparingFoodListLayoutBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.TVOrderId.text = "Order ID : ${orderList[position].id}"

        var foodList : String = ""
        for(cartFood in orderList[position].foodList!!)
            foodList += cartFood.food.name + " X ${cartFood.quantity}\n"

        holder.binding.TVFoodItems.text = foodList

        holder.binding.BTReady.setOnClickListener {
            makeOrderReady(orderList[position])
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class ViewHolder(var binding: ItemPreparingFoodListLayoutBinding) : RecyclerView.ViewHolder(binding.root)


}