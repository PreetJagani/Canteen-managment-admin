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

    private val foodDB = FirebaseFirestore.getInstance()

    suspend fun storeFoodData(food : Food): CustomeResult {
        return withContext(Dispatchers.IO){

            val foodDR = foodDB.collection(BaseUrl.FOOD).document()

            food.id = foodDR.id
            var map = Food.getMapFromFood(food)

            var result = CustomeResult()

            foodDR.set(map).addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Added Successfully"
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message?:"Error"
            }.await()

            return@withContext result
        }
    }

    suspend fun updateFoodData(food : Food): CustomeResult {
        return withContext(Dispatchers.IO){

            val foodDR = food.id?.let { foodDB.collection(BaseUrl.FOOD).document(it) }


            var map = Food.getMapFromFood(food)

            var result = CustomeResult()

            foodDR?.set(map)?.addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Update Successfully"
            }?.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message?:"Error"
            }?.await()

            return@withContext result
        }
    }

    suspend fun deleteFoodData(food : Food): CustomeResult {
        return withContext(Dispatchers.IO){

            val foodDR = food.id?.let { foodDB.collection(BaseUrl.FOOD).document(it) }

            var result = CustomeResult()

            foodDR?.delete()?.addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Delete Successfully"
            }?.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message?:"Error"
            }?.await()

            return@withContext result
        }
    }


    suspend fun getAllFoodFromCategory(category : String): List<Food> {
        val foodDR = foodDB.collection(BaseUrl.FOOD)

        var result : CustomeResult = CustomeResult()

        var snapshot = foodDR.whereEqualTo(Food.CATEGORY,category).get().addOnSuccessListener {
            result.isSuccess = true
        }.addOnFailureListener{
            result.isSuccess = false
            result.message = it.message.toString()
        }.await()

        var b = snapshot.documents.map {
            Food.getFoodFromDocumentSnapShot(it.data!!)
        }

        return b

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

                val reference: StorageReference? =
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
        const val FOOD = "Food"
    }

}