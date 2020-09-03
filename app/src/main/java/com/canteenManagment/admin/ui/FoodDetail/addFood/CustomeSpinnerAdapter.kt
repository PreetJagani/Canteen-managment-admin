package com.canteenManagment.admin.ui.FoodDetail.addFood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ItemCustomSpinnerBinding

class CustomeSpinnerAdapter(context: Context, list: List<Int>) : ArrayAdapter<Int>(context,R.layout.item_custom_spinner,list) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            ItemCustomSpinnerBinding.inflate(LayoutInflater.from(context), parent, false)

        binding.TVtitle.text = getItem(position).toString()

        return binding.root
    }

}