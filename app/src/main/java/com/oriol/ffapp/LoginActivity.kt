package com.oriol.ffapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginUsername = findViewById(R.id.loginUsername)
        loginPassword = findViewById(R.id.loginPassword)

        val btnLogin = findViewById<Button>(R.id.loginButton)
        btnLogin.setOnClickListener {
            postUserLogin(it)
        }

        val btnLoginGuest = findViewById<TextView>(R.id.loginRedirect)
        btnLoginGuest.setOnClickListener{
            val intent = Intent(this@LoginActivity, Menu::class.java)
            startActivity(intent)
        }

        val tvSignupRedirect = findViewById<TextView>(R.id.signupRedirect)
        tvSignupRedirect.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    fun openUserActivity(view: View) {
        if (validateFields()) {
            postUserLogin(view)
        }
    }

    fun postUserLogin(view: View) {
        if (validateFields()) {
            CoroutineScope(Dispatchers.IO).launch {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                val con = Retrofit.Builder().baseUrl(Routes.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                val response = con.create(APIService::class.java).postLogin(
                    loginUsername.text.toString(),
                    loginPassword.text.toString()
                )

                if (response.isSuccessful) {
                    println("Login successful!")

                    val usuario = response.body()
                    if (usuario != null) {
                        println("Valor isCompany (desde response.body): " + usuario.isCompany)

                        // Guardar userId en SharedPreferences
                        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putInt("USER_ID", usuario.idUser ?: -1)  // Evita NPE con Elvis operator
                            putString("Name_User", usuario.nameUser ?: "")
                            putString("SurName_User", usuario.surnameUser ?: "")
                            putString("Email_User", usuario.emailUser ?: "")
                            putString("Username_User", usuario.usernameUser ?: "")
                            putString("Password_User", usuario.passwordUser ?: "")
                            putBoolean("isCompany", usuario.isCompany ?: false)

                            // Verifica el valor guardado en SharedPreferences
                            println("Valor isCompany (desde SharedPreferences): " + sharedPreferences.getBoolean("isCompany", false))

                            apply()
                        }

                        val intent = Intent(this@LoginActivity, Menu::class.java).apply {
                            putExtra("USERNAME_PARAMETRE", loginUsername.text.toString())
                            putExtra("LoggedIn", true)
                            putExtra("isCompany", usuario.isCompany ?: false)
                        }
                        startActivity(intent)
                    }
                } else {
                    showLoginError()
                    println(response.errorBody()?.string())
                    println("Login not successful!")
                }


            }
        }
    }


    private fun validateFields(): Boolean {
        val loginUser = loginUsername.text.toString()
        val loginPassword = loginPassword.text.toString()

        return if (loginUser.isNotEmpty() && loginPassword.isNotEmpty()) {
            true
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun showLoginError() {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
        }
    }
}
