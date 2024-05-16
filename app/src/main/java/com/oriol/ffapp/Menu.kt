// Menu.kt
package com.oriol.ffapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class Menu : AppCompatActivity() {
    private var LoggedIn: Boolean = false // Variable para controlar la visibilidad del botón de perfil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val llista_extres = intent.extras
        val nom = llista_extres?.getString("USERNAME_PARAMETRE")
        println("Username paremetre: $nom")

        // Leer el estado de la variable del Intent
        LoggedIn = llista_extres?.getBoolean("SHOW_PROFILE_BUTTON", false) ?: false

        val btnProfile = findViewById<AppCompatButton>(R.id.btnProfile)
        btnProfile.visibility = if (LoggedIn) View.VISIBLE else View.GONE

        // Configurar listeners de los botones restantes...
    }
}
