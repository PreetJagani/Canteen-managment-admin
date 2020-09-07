package com.canteenManagment.admin.ui.FoodDetail.editFood

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.ui.FoodDetail.listFood.FoodListActivity.Companion.FOOD_ITEM
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ActivityEditFoodBinding
import com.canteenManagment.admin.helper.CustomProgressBar
import com.canteenManagment.admin.helper.DeleteCustomDiolog
import com.canteenManagment.admin.helper.showShortToast
import com.canteenManagment.admin.ui.FoodDetail.addFood.CustomeSpinnerAdapter
import com.canteenManagment.admin.ui.FoodDetail.listFood.FoodListActivity
import com.canteenmanagment.canteen_managment_library.apiManager.CustomeResult
import com.canteenmanagment.canteen_managment_library.apiManager.FirebaseApiManager
import com.canteenmanagment.canteen_managment_library.models.Food
import kotlinx.coroutines.launch

class EditFoodActivity : BaseActivity(), View.OnClickListener, View.OnLongClickListener {

    private lateinit var binding: ActivityEditFoodBinding
    private val mContext: Context = this
    private val progressDialog: CustomProgressBar = CustomProgressBar(this)
    private val deleteDialog: DeleteCustomDiolog = DeleteCustomDiolog(this)
    private var imageUri: Uri? = null
    private lateinit var food: Food

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_edit_food
        )
        setContentView(binding.root)

        food = intent.getSerializableExtra(FOOD_ITEM) as Food

        binding.IMback.setOnClickListener(this)
        binding.TVtitle.text = "Update Item"

        binding.ETname.setText(food.name.toString())
        binding.ETPrice.setText(food.price.toString())

        binding.SPCounterNumber.adapter = CustomeSpinnerAdapter(this, listOf(1, 2, 3, 4, 5))
        food.counterNumber?.let {
            binding.SPCounterNumber.setSelection(it-1,true)
        }
        if(food.availableTimes?.contains("Morning") == true)
            binding.CHMorning.isChecked = true
        if(food.availableTimes?.contains("Afternoon") == true)
            binding.CHAfternoon.isChecked = true
        if(food.availableTimes?.contains("Evening") == true)
            binding.CHEvening.isChecked = true


        Glide.with(this)
            .load(food.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.error_image)
            .error(R.drawable.error_image)
            .into(binding.IMFoodImage)

        binding.BTUpdate.setOnClickListener(this)
        binding.BTDeleteFood.setOnClickListener(this)

        binding.IMFoodImage.setOnClickListener(this)
        binding.IMFoodImage.setOnLongClickListener(this)
    }

    private fun getSelectedChip() : List<String>{

        var timeList = mutableListOf<String>()

        if(binding.CHMorning.isChecked)
            timeList.add("Morning")

        if(binding.CHAfternoon.isChecked)
            timeList.add("Afternoon")

        if(binding.CHEvening.isChecked)
            timeList.add("Evening")

        return timeList
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.IMback -> {
                super.onBackPressed()
            }
            R.id.BT_update -> updateFood()

            R.id.IM_Food_Image -> chooseImage()

            R.id.BT_Delete_food -> deleteDialog.startDialog{
                deleteFood()
            }

        }
    }

    override fun onLongClick(v: View?): Boolean {
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data

            Glide.with(this)
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.error_image)
                .error(R.drawable.error_image)
                .into(binding.IMFoodImage)
        }
    }

    private fun updateFood() {

        progressDialog.startDialog()
        scope.launch {
            uploadImage().let {

                progressDialog.stopDiaolog()

                if (it.isSuccess || it.message.equals("Please Select image")){

                    food.name = binding.ETname.text.toString().trim()
                    food.price = binding.ETPrice.text.toString().trim().toInt()
                    food.counterNumber = binding.SPCounterNumber.selectedItemPosition + 1

                    food.availableTimes = getSelectedChip()

                    if(it.data != null)
                        food.imageUrl = it.data.toString()

                    progressDialog.startDialog()

                    scope.launch {

                        FirebaseApiManager.updateFoodData(food).let {
                            progressDialog.stopDiaolog()
                            when (it.isSuccess) {
                                true -> {
                                    showShortToast(it.message, mContext)
                                    setResult(FoodListActivity.DATA_CHANGE)
                                    super.onBackPressed()
                                }

                                false -> showShortToast(it.message, mContext)
                            }
                        }
                    }

                }

                else
                    showShortToast(it.message,mContext)
            }
        }
    }

    private suspend fun uploadImage() : CustomeResult {
        if (imageUri != null) {

            val cr = contentResolver
            val mine = MimeTypeMap.getSingleton()

            var filename = binding.ETname.text.toString()
                .trim() + "." + mine.getExtensionFromMimeType(imageUri?.let { cr.getType(it) })

            FirebaseApiManager.uploadFile(imageUri!!,
                filename,
                FirebaseApiManager.BaseUrl.FOOD + "/" + binding.ETname.text.toString().trim()).let {

                return it
            }

        } else
            return CustomeResult(false,"Please Select image")

    }

    private fun deleteFood(){
        scope.launch {
            FirebaseApiManager.deleteFoodData(food).let {
                deleteDialog.stopDiaolog()
                when (it.isSuccess) {
                    true -> {
                        showShortToast(it.message, mContext)
                        setResult(FoodListActivity.DATA_CHANGE)
                        super.onBackPressed()
                    }

                    false -> showShortToast(it.message, mContext)
                }
            }
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CHOOSE_IMAGE)
    }

    companion object {
        const val CHOOSE_IMAGE = 2001
    }
}

