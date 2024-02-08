package com.oriol.ffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnAboutUs = findViewById<AppCompatButton>(R.id.btnAboutUs)
        btnAboutUs.setOnClickListener{
            val intent = Intent(this@Menu, AboutUs::class.java)
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
    }
}