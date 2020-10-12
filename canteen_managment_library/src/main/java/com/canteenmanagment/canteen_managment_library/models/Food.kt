package com.canteenmanagment.canteen_managment_library.models

import java.io.Serializable

data class Food(
    var id : String?,
    var name : String?,
    var price : Int?,
    var imageUrl : String?,
    var counterNumber : Int?,
    var category : String?,
    var available : Boolean,
    var availableTimes : List<String>?
) : Serializable {

    constructor() : this(null,null,null, null,null,null,false,null)

    companion object{

        fun getMapFromFood(food : Food): MutableMap<String, Any> {

            var map = mutableMapOf<String,Any>()

            map[ID] = food.id?:"default id"
            map[NAME] = food.name?:"default name"
            map[PRICE] = food.price?:0
            map[IMAGE_URL] = food.imageUrl?:"default url"
            map[COUNTER_NUMBER] = food.counterNumber?:0
            map[AVAILABLE] = food.available
            map[CATEGORY] = food.category?:"default category"
            map[AVAILABLE_TIMES] = food.availableTimes?: emptyList<String>()

            return map
        }

        fun getFoodFromDocumentSnapShot(map : MutableMap<String,Any>) : Food{

            return Food(
                id = map[ID] as String?,
                name = map[NAME] as String?,
                price = (map[PRICE] as Long).toInt(),
                counterNumber = (map[COUNTER_NUMBER] as Long).toInt(),
                imageUrl = map[IMAGE_URL] as String?,
                category = map[CATEGORY] as String?,
                available = map[AVAILABLE] as Boolean,
                availableTimes = (map[AVAILABLE_TIMES]?: emptyList<String>()) as List<String>
            )

        }


        const val ID = "id"
        const val NAME = "name"
        const val PRICE = "price"
        const val IMAGE_URL = "imageurl"
        const val COUNTER_NUMBER = "counterNumber"
        const val CATEGORY = "Category"
        const val AVAILABLE = "available"
        const val AVAILABLE_TIMES = "availableTimes"
    }



    enum class Category(val value : String) {
        SNACKS("Snacks"),
        FIX_THALI("Fix Thali"),
        DRINKS("Drinks"),
        PUNJABI_MEAL("Punjabi Meal")
    }





}
