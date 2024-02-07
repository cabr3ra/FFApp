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
    }
}