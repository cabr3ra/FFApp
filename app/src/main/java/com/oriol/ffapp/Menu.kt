package com.oriol.ffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class Menu : AppCompatActivity() {
    private var LoggedIn: Boolean = false // Variable que indica si el usuario ha iniciado sesión correctamente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnProfile = findViewById<AppCompatButton>(R.id.btnProfile)

        val llista_extres = intent.extras
        val nom = llista_extres?.getString("USERNAME_PARAMETRE")
        val isLoggedIn = llista_extres?.getBoolean("LoggedIn", false) ?: false

        if (isLoggedIn) {
            btnProfile.visibility = View.VISIBLE
            btnProfile.setOnClickListener{
                val intent = Intent(this@Menu, Profile::class.java)
                startActivity(intent)
            }
        } else {
            btnProfile.visibility = View.GONE
        }

        val btnComparator = findViewById<AppCompatButton>(R.id.btnComparator)
        btnComparator.setOnClickListener{
            val intent = Intent(this@Menu, Comparator::class.java)
            startActivity(intent)
        }

        val btnFruitShopList = findViewById<AppCompatButton>(R.id.btnFruitShopList)
        btnFruitShopList.setOnClickListener{
            val intent = Intent(this@Menu, FruitShopList::class.java)
            startActivity(intent)
        }

        val btnMaps = findViewById<AppCompatButton>(R.id.btnMaps)
        btnMaps.setOnClickListener{
            val intent = Intent(this@Menu, Maps::class.java)
            startActivity(intent)
        }

        val btnAboutUs = findViewById<AppCompatButton>(R.id.btnAboutUs)
        btnAboutUs.setOnClickListener{
            val intent = Intent(this@Menu, AboutUs::class.java)
            startActivity(intent)
        }

        val textView = findViewById<TextView>(R.id.textViewTitle)
        if (nom != null){
            textView.text = getString(R.string.bienvenido) + " " + nom
        } else{
            textView.text = getString(R.string.bienvenido)
        }
    }
}