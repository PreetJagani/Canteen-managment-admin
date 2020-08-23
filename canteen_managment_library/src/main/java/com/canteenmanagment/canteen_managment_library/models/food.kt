package com.canteenmanagment.canteen_managment_library.models

class food(
    var name : String?,
    var price : Int?,
    var imageUrls : List<String>,
    var counterNumber : Int?,
    var category : String?,
    var available : Boolean
) {

    constructor() : this(null,null, emptyList<String>(),null,null,false)



}