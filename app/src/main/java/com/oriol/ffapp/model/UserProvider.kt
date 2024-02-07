package com.oriol.ffapp.model

class UserProvider {
    companion object {
        val users = mutableListOf<User>(
            User("Maria", "Gimena", "mariagimena@gmail.com", "mgimena", "1234", false, false),
            User("Anton", "Guason", "antonguason@gmail.com", "aguason", "1234", false, false),
            User("Oriol", "Cabrera", "oriolcabrera@gmail.com", "ocabrera", "1234", true, false),
        )
    }
}
