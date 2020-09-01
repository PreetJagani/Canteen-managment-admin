package com.canteenManagment.admin.MenuPage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.Fragments.MenuFragment.Companion.CATEGORY_NAME
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ActivityAddFoodBinding
import com.canteenManagment.admin.helper.CustomProgressBar
import com.canteenManagment.admin.helper.showShortToast
import com.canteenmanagment.canteen_managment_library.apiManager.CustomeResult
import com.canteenmanagment.canteen_managment_library.apiManager.FirebaseApiManager
import com.canteenmanagment.canteen_managment_library.models.Food
import kotlinx.coroutines.launch

class AddFoodActivity : BaseActivity(), View.OnClickListener, View.OnLongClickListener {

    private lateinit var binding: ActivityAddFoodBinding
    private val mContext: Context = this
    private val progressDialog: CustomProgressBar = CustomProgressBar(this)
    private var imageUri: Uri? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_food
        )
        setContentView(binding.root)

        binding.IMback.setOnClickListener(this)
        binding.TVtitle.text = "Add ${intent.getStringExtra(CATEGORY_NAME)}"

        binding.BTadd.setOnClickListener(this)

        binding.SPCounterNumber.adapter = CustomeSpinnerAdapter(this, listOf(1, 2, 3, 4, 5))

        binding.IMFoodImage.setOnClickListener(this)
        binding.IMFoodImage.setOnLongClickListener(this)


    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.IMback -> {
                super.onBackPressed()
                overridePendingTransition(
                    android.R.anim.fade_in,
                    R.anim.slide_out_bottom
                )
            }
            R.id.BTadd -> addFood()

            R.id.IM_Food_Image -> chooseImage()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_top,
            R.anim.slide_out_bottom
        )
    }

    override fun onLongClick(v: View?): Boolean {
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.IMFoodImage.setImageURI(data.data)
        }
    }

    private fun addFood() {

        progressDialog.startDialog()
        scope.launch {
            uploadImage().let {

                progressDialog.stopDiaolog()

                if (it.isSuccess){

                    var food = Food()
                    food.name = binding.ETname.text.toString().trim()
                    food.price = binding.ETPrice.text.toString().trim().toInt()
                    food.counterNumber = binding.SPCounterNumber.selectedItemPosition + 1
                    food.category = intent.getStringExtra(CATEGORY_NAME)
                    food.available = true
                    food.imageUrl = it.data.toString()

                    progressDialog.startDialog()

                    scope.launch {

                        FirebaseApiManager.storeFoodData(food).let {
                            progressDialog.stopDiaolog()
                            when (it.isSuccess) {
                                true -> showShortToast(it.message, mContext)

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

