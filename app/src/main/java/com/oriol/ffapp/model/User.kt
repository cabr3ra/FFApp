package com.oriol.ffapp.model

data class User(
    val name: String?,
    val surname: String?,
    val email: String?,
    val username: String,
    val password: String,
    val admin: Boolean,
    val baja: Boolean

)
