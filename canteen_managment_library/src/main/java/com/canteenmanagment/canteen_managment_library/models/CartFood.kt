package com.canteenmanagment.canteen_managment_library.models

import java.io.Serializable

data class CartFood(
    var quantity : Int,
    var food : Food
) : Serializable