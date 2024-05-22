package com.oriol.ffapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.rvUser.UserRvAdapter
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.RetrofitClient
import com.oriol.ffapp.server.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Profile : AppCompatActivity() {

    private var userId: Int? = null  // Cambiado a Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Obtener el userId desde SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("USER_ID", -1).takeIf { it != -1 }

        if (userId == null) {
            Toast.makeText(this, "Error al obtener el ID de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProfiles)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnBaja = findViewById<Button>(R.id.btn_baja)
        btnBaja.setOnClickListener {
            userId?.let { id ->
                showConfirmationDialog(id)
            } ?: Toast.makeText(this, "Seleccione un usuario", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            val response = RetrofitClient.apiService.getUsers()
            if (response.isSuccessful && response.body() != null) {
                val userList = response.body()!!
                val adapter = UserRvAdapter(userList) { user ->
                    userId = user.idUser  // Asegurarse de que idUser es Int
                }
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(this@Profile, "Error al obtener datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConfirmationDialog(userId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que quieres eliminar este usuario?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            deleteUser(userId)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun deleteUser(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Routes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val response = retrofit.create(APIService::class.java).deleteUser(userId)
            if (response.isSuccessful) {
                runOnUiThread {
                    Toast.makeText(this@Profile, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    // Redirigir a LoginActivity
                    val intent = Intent(this@Profile, SignupActivity::class.java)
                    startActivity(intent)
                    finish()  // Finalizar la actividad actual
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@Profile, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
