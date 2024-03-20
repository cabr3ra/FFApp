package com.oriol.ffapp.model

class UserProvider {
    companion object {
        val users = mutableListOf<User>(
            User(0,"", "", "", "", "", false, false),
        )
    }
}
