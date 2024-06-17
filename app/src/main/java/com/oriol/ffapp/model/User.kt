package com.oriol.ffapp.model

data class User(
    val idUser: Int? = null,
    val emailUser: String? = null,
    val nameUser: String? = null,
    val passwordUser: String,
    val surnameUser: String? = null,
    val usernameUser: String,
    val isCompany: Boolean = false,
    val fruitShop: FruitShop? = null
)
