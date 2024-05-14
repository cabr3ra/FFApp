package com.oriol.ffapp.model

data class Price(
    val price: Float,
    val fruitName: String,
    val fruitShopName: String
) {
    companion object {
        fun fromArray(array: List<Any>): Price {
            return Price(
                price = (array[0] as? Double)?.toFloat() ?: 0.0f,
                fruitName = array[1] as? String ?: "",
                fruitShopName = array[2] as? String ?: ""
            )
        }
    }
}



