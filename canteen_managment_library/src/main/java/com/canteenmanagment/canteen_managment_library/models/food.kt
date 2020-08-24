package com.canteenmanagment.canteen_managment_library.models

class food(
    var name : String?,
    var price : Int?,
    var imageUrls : String?,
    var counterNumber : Int?,
    var category : String?,
    var available : Boolean
) {

    constructor() : this(null,null, null,null,null,false)


    enum class Category(val value : String) {
        SNACKS("Snacks"),
        FIX_THALI("Fix Thali"),
        DRINKS("Drinks"),
        PUNJABI_MEAL("Punjabi Meal")
    }


}