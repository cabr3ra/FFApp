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
import com.oriol.ffapp.model.User
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

    private var currentUser: User? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Obtener los datos del usuario logueado desde SharedPreferences
        currentUser = getCurrentUser()

        if (currentUser == null) {
            Toast.makeText(this, "Error al obtener el ID de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        recyclerView = findViewById(R.id.recyclerViewProfiles)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnBaja = findViewById<Button>(R.id.btn_baja)
        btnBaja.setOnClickListener {
            currentUser?.let { user ->
                user.idUser?.let { id ->
                    showConfirmationDialog(id)
                } ?: Toast.makeText(this, "ID de usuario no disponible", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
        }

        // Mostrar los datos del usuario logueado
        displayCurrentUser()
    }

    private fun getCurrentUser(): User? {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getInt("USER_ID", -1)
        if (idUser == -1) {
            return null
        }
        val nameUser = sharedPreferences.getString("Name_User", null)
        val surnameUser = sharedPreferences.getString("SurName_User", null)
        val emailUser = sharedPreferences.getString("Email_User", null)
        val usernameUser = sharedPreferences.getString("Username_User", null)
        val passwordUser = sharedPreferences.getString("Password_User", null)

        return User(idUser, nameUser, surnameUser, emailUser, usernameUser, passwordUser)
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
            runOnUiThread {
                if (response.isSuccessful) {
                    Toast.makeText(this@Profile, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    // Redirigir a LoginActivity
                    val intent = Intent(this@Profile, LoginActivity::class.java)
                    startActivity(intent)
                    finish()  // Finalizar la actividad actual
                } else {
                    Toast.makeText(this@Profile, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayCurrentUser() {
        currentUser?.let { user ->
            val userList = listOf(user) // Crear una lista con solo el usuario actual
            val adapter = UserRvAdapter(userList) { selectedUser ->
                // Aquí no es necesario hacer nada porque solo mostramos un usuario
            }
            recyclerView.adapter = adapter
        } ?: run {
            Toast.makeText(this, "Error al mostrar los datos del usuario", Toast.LENGTH_SHORT).show()
        }
    }
}

