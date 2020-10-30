package com.canteenmanagment.canteen_managment_library.apiManager

import android.net.Uri
import android.util.Log
import com.canteenmanagment.canteen_managment_library.models.CartFood
import com.canteenmanagment.canteen_managment_library.models.Food
import com.canteenmanagment.canteen_managment_library.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

object FirebaseApiManager {

    private val DB = FirebaseFirestore.getInstance()
//    private val uid = FirebaseAuth.getInstance().uid
    private val uid = "TrtfkHPfrUSK8kkaNWnXbL0DBzK2"

    suspend fun storeFoodData(food: Food): CustomeResult {
        return withContext(Dispatchers.IO) {

            val foodDR = DB.collection(BaseUrl.FOOD).document()

            food.id = foodDR.id
            var map = Food.getMapFromFood(food)

            var result = CustomeResult()

            foodDR.set(map).addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Added Successfully"
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message ?: "Error"
            }.await()

            return@withContext result
        }
    }

    suspend fun updateFoodData(food: Food): CustomeResult {
        return withContext(Dispatchers.IO) {

            val foodDR = food.id?.let { DB.collection(BaseUrl.FOOD).document(it) }


            var map = Food.getMapFromFood(food)

            var result = CustomeResult()

            foodDR?.set(map)?.addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Update Successfully"
            }?.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message ?: "Error"
            }?.await()

            return@withContext result
        }
    }

    suspend fun deleteFoodData(food: Food): CustomeResult {
        return withContext(Dispatchers.IO) {

            val foodDR = food.id?.let { DB.collection(BaseUrl.FOOD).document(it) }

            var result = CustomeResult()

            foodDR?.delete()?.addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Delete Successfully"
            }?.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message ?: "Error"
            }?.await()

            return@withContext result
        }
    }


    suspend fun getAllFoodFromCategory(category: String): List<Food> {
        val foodDR = DB.collection(BaseUrl.FOOD)

        var result: CustomeResult = CustomeResult()

        var snapshot = foodDR.whereEqualTo(Food.CATEGORY, category).get().addOnSuccessListener {
            result.isSuccess = true
        }.addOnFailureListener {
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
        var result: CustomeResult = CustomeResult()
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

    suspend fun placeOrderInSystem(
        foodList: MutableList<CartFood>,
        transactionID: String
    ): CustomeResult {

        val result: CustomeResult = CustomeResult()
        return withContext(Dispatchers.IO) {
            try {
                val orderDR = DB.collection(BaseUrl.ORDER).document()
                val order = Order()
                order.id = orderDR.id
                order.foodList = foodList
                order.status = Order.Status.INPROGRESS.value
                order.uId = uid
                order.time = Date().time
                order.transactionId = transactionID

                orderDR.set(Order.getMapFromOrder(order)).addOnSuccessListener {
                    result.isSuccess = true
                }.addOnFailureListener {
                    result.isSuccess = false
                    result.message = it.message.toString()
                }.await()

                result
            } catch (e: Exception) {
                result.isSuccess = false
                result.message = e.message.toString()
                result
            }
        }
    }

    /*suspend fun placeOrderWithPendingPayment(){
    }

    suspend fun setTransactionIdToOrder(){
    }

    suspend fun deleteOrderFromID(){
    }*/

    suspend fun getInProgressOrder(): CustomeResult {
        val orderDR = DB.collection(BaseUrl.ORDER)

        val result: CustomeResult = CustomeResult()

        val snapshot = orderDR.whereEqualTo(Order.UID, uid)
            .whereEqualTo(Order.STATUS, Order.Status.INPROGRESS.value)
            .orderBy(Order.TIME, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                result.isSuccess = true
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message.toString()
            }.await()

        if (result.isSuccess)
            result.data = snapshot.documents.map {
                Order.getOrderFromDocumentSnapShot(it.data!!)
            }

        return result
    }

    suspend fun getReadyOrder(): CustomeResult {
        val orderDR = DB.collection(BaseUrl.ORDER)

        val result: CustomeResult = CustomeResult()

        val snapshot = orderDR.whereEqualTo(Order.UID, uid)
            .whereEqualTo(Order.STATUS, Order.Status.READY.value)
            .orderBy(Order.TIME, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                result.isSuccess = true
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message.toString()
            }.await()

        if (result.isSuccess)
            result.data = snapshot.documents.map {
                Order.getOrderFromDocumentSnapShot(it.data!!)
            }

        return result
    }

    suspend fun getAllPastOrders(): CustomeResult {
        val orderDR = DB.collection(BaseUrl.ORDER)

        val result: CustomeResult = CustomeResult()

        val snapshot = orderDR.whereEqualTo(Order.UID, uid)
            .whereEqualTo(Order.STATUS, Order.Status.SUCCESS.value)
            .orderBy(Order.TIME, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                result.isSuccess = true
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message.toString()
            }.await()

        if (result.isSuccess)
            result.data = snapshot.documents.map {
                Order.getOrderFromDocumentSnapShot(it.data!!)
            }
        return result

    }

    suspend fun getAllFavouriteFoods(): CustomeResult {

        val foodDR = DB.collection(BaseUrl.USER).document(uid!!).collection(BaseUrl.FAVOURITE)

        val result: CustomeResult = CustomeResult()

        val snapshot = foodDR.get().addOnSuccessListener {
            result.isSuccess = true
        }.addOnFailureListener {
            result.isSuccess = false
            result.message = it.message.toString()
        }.await()

        if(result.isSuccess)
            result.data = snapshot.documents.map {
                Food.getFoodFromDocumentSnapShot(it.data!!)
            }

        return result
    }

    suspend fun addFoodToFavourite(food: Food): CustomeResult {
        return withContext(Dispatchers.IO) {

            val favFoodDR =
                DB.collection(BaseUrl.USER).document(uid!!).collection(BaseUrl.FAVOURITE)
                    .document(food.id!!)

            val map = Food.getMapFromFood(food)

            val result = CustomeResult()

            favFoodDR.set(map).addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Added Successfully"
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message ?: "Error"
            }.await()

            return@withContext result
        }
    }

    suspend fun removeFoodFromFavourite(food: Food): CustomeResult {

        return withContext(Dispatchers.IO) {

            val favFoodDR =
                DB.collection(BaseUrl.USER).document(uid!!).collection(BaseUrl.FAVOURITE)
                    .document(food.id!!)

            var result = CustomeResult()

            favFoodDR.delete().addOnCompleteListener {
                result.isSuccess = true
                result.message = "Food Delete Successfully"
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message ?: "Error"
            }.await()

            return@withContext result
        }

    }

    suspend fun getAllPastFoods(): CustomeResult {

        val ORDER_LIMIT = 5

        val orderDR = DB.collection(BaseUrl.ORDER)

        val result: CustomeResult = CustomeResult()

        val snapshot = orderDR.whereEqualTo(Order.UID, uid)
            .whereEqualTo(Order.STATUS, Order.Status.SUCCESS.value)
            .orderBy(Order.TIME, Query.Direction.DESCENDING)
            .limit(ORDER_LIMIT.toLong())
            .get()
            .addOnSuccessListener {
                result.isSuccess = true
            }.addOnFailureListener {
                result.isSuccess = false
                result.message = it.message.toString()
            }.await()

        if (result.isSuccess) {
            val orderList = snapshot.documents.map {
                Order.getOrderFromDocumentSnapShot(it.data!!)
            }

            val foodList : MutableList<Food> = mutableListOf<Food>()
            for(order in orderList)
                for(cartFood in order.foodList?: listOf<CartFood>())
                    if(foodList.indexOf(cartFood.food) == -1) {
                        foodList.add(cartFood.food)
                        Log.d("Fav",cartFood.food.imageUrl?:"null url")
                    }

            result.data = foodList
        }

        return result
    }



    object BaseUrl {
        const val FOOD = "Food"
        const val ORDER = "Order"
        const val USER = "User"
        const val FAVOURITE = "Favourite"
    }

}