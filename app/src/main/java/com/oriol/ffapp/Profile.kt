package com.oriol.ffapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.rvUser.UserRvAdapter
import com.oriol.ffapp.server.RetrofitClient
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProfiles)
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*val btnBaja = findViewById<Button>(R.id.btn_baja)
        btnBaja.setOnClickListener {
            val intent = Intent(this@Profile, LoginActivity::class.java)
            startActivity(intent)
        }*/

        lifecycleScope.launch {

                val response = RetrofitClient.apiService.getUsers()
                if (response.isSuccessful && response.body() != null) {
                    val userList = response.body()!!
                    val adapter = UserRvAdapter(userList)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@Profile, "Error al obtener datos", Toast.LENGTH_SHORT).show()
                }

        }
    }
}