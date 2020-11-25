package com.canteenManagment.admin.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.canteenManagment.admin.BaseActivity.BaseActivity
import com.canteenManagment.admin.R
import com.canteenManagment.admin.databinding.ActivityScanBinding
import com.canteenManagment.admin.helper.CustomProgressBar
import com.canteenManagment.admin.helper.showShortToast
import com.canteenmanagment.canteen_managment_library.apiManager.FirebaseApiManager
import com.google.zxing.BarcodeFormat
import kotlinx.coroutines.launch

class ScanActivity : BaseActivity() {

    lateinit var binding: ActivityScanBinding
    private lateinit var codeScanner: CodeScanner
    private val mContext = this
    private lateinit var progressDialog: CustomProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_scan
        )
        setContentView(binding.root)

        binding.IMback.setOnClickListener {
            super.onBackPressed()
        }

        progressDialog = CustomProgressBar(this)

        codeScanner = CodeScanner(this, binding.CS)

        // Parameters (default values)
        codeScanner.formats = listOf(BarcodeFormat.QR_CODE) // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                makeOrderSuccess(it.text)
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        binding.CS.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    private fun makeOrderSuccess(orderId : String){

        progressDialog.startDialog()
        scope.launch {
            FirebaseApiManager.makeOrderSuccess(orderId).let {
                progressDialog.stopDiaolog()
                if(it.isSuccess)
                    showShortToast("Order Given Successfully",mContext)
                else
                    showShortToast(it.message,mContext)

            }
        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}