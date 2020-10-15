package com.canteenmanagment.canteen_managment_library.models

import java.io.Serializable

data class CartFood(
    var quantity : Int,
    var food : Food
) : Serializable {

    companion object{
        fun getCartFoodFromMap(map : MutableMap<String,Any>) : CartFood{
            return CartFood(
                quantity = (map["quantity"] as Long).toInt(),
                food =  Food.getFoodFromDocumentSnapShot(map["food"] as MutableMap<String,Any>)
            )
        }

        fun getListFromListMap(list : List<Any>) : MutableList<CartFood>{

            var cartFoodList : MutableList<CartFood> = mutableListOf()

            for(item in list)
                cartFoodList.add(getCartFoodFromMap(item as MutableMap<String, Any>))

            return cartFoodList
        }
    }


}