package com.oriol.ffapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.model.User
import com.oriol.ffapp.rvUser.UserRvAdapter
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response

class Profile : AppCompatActivity() {

    private var currentUser: User? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var editName: EditText
    private lateinit var editSurname: EditText
    private lateinit var editEmail: EditText
    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializar vistas
        val btnLogout: Button = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener {
            // Crea un Intent para iniciar LoginActivity
            val intent = Intent(this@Profile, LoginActivity::class.java)

            // Inicia LoginActivity y cierra la actividad actual
            startActivity(intent)
            finish()
        }

        currentUser = getCurrentUser()

        if (currentUser == null) {
            Toast.makeText(this, "Error al obtener el ID de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        recyclerView = findViewById(R.id.recyclerViewProfiles)
        recyclerView.layoutManager = LinearLayoutManager(this)
        editName = findViewById(R.id.editName)
        editSurname = findViewById(R.id.editSurname)
        editUsername = findViewById(R.id.editUsername)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)

        val btnBaja = findViewById<Button>(R.id.btn_baja)
        btnBaja.setOnClickListener {
            currentUser?.let { user ->
                user.idUser?.let { id ->
                    showConfirmationDialog(id)
                } ?: Toast.makeText(this, "ID de usuario no disponible", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
        }

        val btnModificar = findViewById<Button>(R.id.btn_modificar)
        btnModificar.setOnClickListener {
            currentUser?.let { user ->
                user.idUser?.let { id ->
                    updateUser(id)
                } ?: Toast.makeText(this, "ID de usuario no disponible", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
        }

        // Mostrar los datos del usuario logueado
        displayCurrentUser()
    }

    private fun getCurrentUser(): User? {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getInt("USER_ID", -1)
        val isCompany = sharedPreferences.getBoolean("Is_Company", false)

        if (idUser == -1) {
            return null
        }
        val nameUser = sharedPreferences.getString("Name_User", null)
        val surnameUser = sharedPreferences.getString("SurName_User", null)
        val emailUser = sharedPreferences.getString("Email_User", null)
        val usernameUser = sharedPreferences.getString("Username_User", null)
        val passwordUser = sharedPreferences.getString("Password_User", null)

        return User(
            idUser,
            nameUser ?: "",
            surnameUser ?: "",
            emailUser ?: "",
            usernameUser ?: "",
            passwordUser ?: "",
            isCompany
        )
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

            // Mostrar los datos en los campos de entrada
            editName.setText(user.nameUser)
            editSurname.setText(user.surnameUser)
            editEmail.setText(user.emailUser)
            editUsername.setText(user.usernameUser)
            editPassword.setText(user.passwordUser)
        } ?: run {
            Toast.makeText(this, "Error al mostrar los datos del usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUser(userId: Int) {
        val updatedUser = User(
            idUser = userId,
            nameUser = editName.text.toString(),
            surnameUser = editSurname.text.toString(),
            emailUser = editEmail.text.toString(),
            usernameUser = editUsername.text.toString(),
            passwordUser = editPassword.text.toString(),


        )

        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Routes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val response: Response<Void> = retrofit.create(APIService::class.java).putUpdateUser(updatedUser)
            runOnUiThread {
                if (response.isSuccessful) {
                    Toast.makeText(this@Profile, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                    // Actualizar los datos en SharedPreferences
                    val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putString("Name_User", updatedUser.nameUser)
                        putString("SurName_User", updatedUser.surnameUser)
                        putString("Email_User", updatedUser.emailUser)
                        putString("Username_User", updatedUser.usernameUser)
                        putString("Password_User", updatedUser.passwordUser)
                        apply()
                    }
                } else {
                    Toast.makeText(this@Profile, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}