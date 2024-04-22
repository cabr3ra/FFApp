package com.oriol.ffapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<AppCompatButton>(R.id.btnStart)
        btnStart.setOnClickListener{
            val intent = Intent(this@MainActivity, Menu::class.java)
            startActivity(intent)
        }

    }

}