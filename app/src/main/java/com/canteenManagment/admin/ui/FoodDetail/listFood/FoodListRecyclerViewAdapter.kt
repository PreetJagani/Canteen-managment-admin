package com.canteenManagment.admin.ui.FoodDetail.listFood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ItemFoodListLayoutBinding
import com.canteenmanagment.canteen_managment_library.models.Food

class FoodListRecyclerViewAdapter(val foodList : List<Food>, val listner: clickListner) : RecyclerView.Adapter<FoodListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodListLayoutBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.TVTitle.text = foodList.get(position).name
        holder.binding.TVPrice.text = foodList.get(position).price.toString() + " Rs."

        holder.binding.CL.setOnClickListener {
            listner.openActivity(position)
        }


        Glide.with(holder.binding.root)
            .load(foodList.get(position).imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.error_image)
            .error(R.drawable.error_image)
            .into(holder.binding.IMFoodImage)

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class ViewHolder(var binding: ItemFoodListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    class clickListner(val openActivity : (position : Int)-> Unit)

}