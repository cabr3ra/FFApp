package com.oriol.ffapp.model

class UserProvider {
    companion object {
        val users = mutableListOf<User>(
            User(
                idUser = 0,
                usernameUser = "",
                passwordUser = "",
                emailUser = "",
                nameUser = "",
                surnameUser = ""
            )
        )
    }
}
