package com.oriol.ffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnProfile = findViewById<AppCompatButton>(R.id.btnProfile)
        btnProfile.setOnClickListener{
            val intent = Intent(this@Menu, Profile::class.java)
            startActivity(intent)
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

        val btnAboutUs = findViewById<AppCompatButton>(R.id.btnAboutUs)
        btnAboutUs.setOnClickListener{
            val intent = Intent(this@Menu, AboutUs::class.java)
            startActivity(intent)
        }

        val llista_extres = intent.extras
        val nom = llista_extres?.getString("USERNAME_PARAMETRE")
        println("Username paremetre: $nom")

        val textView = findViewById<TextView>(R.id.textViewTitle)
        textView.text = getString(R.string.bienvenido) + nom

    }
}