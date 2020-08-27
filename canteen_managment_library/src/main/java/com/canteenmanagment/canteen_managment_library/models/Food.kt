package com.canteenmanagment.canteen_managment_library.models

class Food(
    var id : String?,
    var name : String?,
    var price : Int?,
    var imageUrl : String?,
    var counterNumber : Int?,
    var category : String?,
    var available : Boolean
) {

    constructor() : this(null,null,null, null,null,null,false)



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

            return map
        }


        const val ID = "id"
        const val NAME = "name"
        const val PRICE = "price"
        const val IMAGE_URL = "imageurl"
        const val COUNTER_NUMBER = "counterNumber"
        const val CATEGORY = "Category"
        const val AVAILABLE = "available"
    }



    enum class Category(val value : String) {
        SNACKS("Snacks"),
        FIX_THALI("Fix Thali"),
        DRINKS("Drinks"),
        PUNJABI_MEAL("Punjabi Meal")
    }





}
