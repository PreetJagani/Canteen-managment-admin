package com.canteenmanagment.canteen_managment_library.apiManager

import android.net.Uri
import com.canteenmanagment.canteen_managment_library.models.Food
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object FirebaseApiManager {

    suspend fun storeFoodData(food : Food): CustomeResult {

        return withContext(Dispatchers.IO){

            var db = FirebaseFirestore.getInstance()

            var dr = db.collection(BaseUrl.FOOD).document()

            food.id = dr.id
            var map = Food.getMapFromFood(food)

            var value = CustomeResult()

            dr.set(map).addOnCompleteListener {
                value.isSuccess = true
                value.message = "Food Added Successfully"
            }.addOnFailureListener {
                value.isSuccess = false
                value.message = it.message?:"Error"
            }.await()

            return@withContext value
        }

    }

    suspend fun uploadFile(
        fileURI: Uri,
        fileName: String,
        uploadPath: String
    ): CustomeResult {
        var result : CustomeResult = CustomeResult()
        return withContext(Dispatchers.IO) {
            try {

                var downloadUrl: String? = null

                var reference: StorageReference? =
                    FirebaseStorage.getInstance().getReference(uploadPath)

                val mref = reference!!.child(fileName)

                mref.putFile(fileURI).await()

                mref.downloadUrl.addOnCompleteListener { task ->
                    downloadUrl = task.result.toString()
                }.addOnFailureListener {
                    result.message = it.message.toString()
                }.await()

                if (downloadUrl != null) {
                    result.isSuccess = true
                    result.data = downloadUrl
                    result
                } else {
                    result.isSuccess = false
                    result
                }

            } catch (e: Exception) {
                result.isSuccess = false
                result.message = e.message.toString()
                result
            }
        }

    }

    object BaseUrl{
        const val FOOD = "FoodTest"
    }

}