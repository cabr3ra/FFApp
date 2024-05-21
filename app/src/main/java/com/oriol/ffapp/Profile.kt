package com.oriol.ffapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.rvUser.UserRvAdapter
import com.oriol.ffapp.server.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProfiles)
        recyclerView.layoutManager = LinearLayoutManager(this)

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
