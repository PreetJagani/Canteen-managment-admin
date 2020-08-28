package com.canteenmanagment.canteen_managment_library.apiManager

import com.canteenmanagment.canteen_managment_library.models.Food
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.Result as Result

object FirebaseApiManager {

    suspend fun storeFoodData(food : Food): CustomeResult {

        return withContext(Dispatchers.IO){

            var db = FirebaseFirestore.getInstance()

            var dr = db.collection(BaseUrl.FOOD).document()

            food.id = dr.id
            var map = Food.getMapFromFood(food)

            var value = CustomeResult()

            dr.set(map).addOnCompleteListener {
                value.success = true
                value.message = "Food Added Successfully"
            }.addOnFailureListener {
                value.success = false
                value.message = it.message?:"Error"
            }.await()

            return@withContext value
        }

    }

    object BaseUrl{
        const val FOOD = "FoodTest"
    }

}