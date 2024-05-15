package com.oriol.ffapp.model

data class User(
    val idUser: Int?,
    val nameUser: String?,
    val surnameUser: String?,
    val emailUser: String?,
    val usernameUser: String?,
    val passwordUser: String?,
    val adminUser: Byte,
    val bajaUser: Byte
)
