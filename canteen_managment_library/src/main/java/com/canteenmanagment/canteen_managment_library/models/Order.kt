package com.canteenmanagment.canteen_managment_library.models

import java.io.Serializable

data class Order(
    var id: String?,
    var foodList: MutableList<CartFood>?,
    var status: String?,
    var uId: String?,
    var time: Long?,
    var transactionId : String?
) : Serializable {

    constructor() : this(null,null,null,null,null,null)

    companion object{
        fun getMapFromOrder(order: Order) : MutableMap<String,Any> {

            val map = mutableMapOf<String,Any>()

            map[ID] = order.id?:""
            map[FOOD_LIST] = order.foodList?: mutableListOf<CartFood>()
            map[STATUS] = order.status?:""
            map[UID] = order.uId?:""
            map[TIME] = order.time?:0
            map[TRANSACTION_ID] = order.transactionId?:""
            return map
        }

        fun getOrderFromDocumentSnapShot(map : MutableMap<String,Any>) : Order{

            return Order(
                id = map[ID] as String,
                foodList = map[FOOD_LIST] as MutableList<CartFood>,
                status = map[STATUS] as String,
                uId = map[UID] as String,
                time = map[TIME] as Long,
                transactionId = map[TRANSACTION_ID] as String
            )
        }

        const val ID = "id"
        const val FOOD_LIST = "foodList"
        const val STATUS = "status"
        const val UID = "uId"
        const val TIME = "time"
        const val TRANSACTION_ID = "transactionId"
    }

    enum class Status(var value: String) {
        SUCCESS("Success"),
        READY("Ready"),
        INPROGRESS("In Progress")
    }

}