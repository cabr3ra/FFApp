package com.oriol.ffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.oriol.ffapp.model.User
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignupActivity : AppCompatActivity() {
    private lateinit var signupUsername: EditText
    private lateinit var signupPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        signupUsername = findViewById(R.id.signupUsername)
        signupPassword = findViewById(R.id.signupPassword)


        val btnSignup = findViewById<Button>(R.id.signupButton)
        btnSignup.setOnClickListener {
            postUserRegister(it)
        }


        val tvLoginRedirect = findViewById<TextView>(R.id.loginRedirect)
        tvLoginRedirect.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun postUserRegister(view: View) {
        if (validateFields()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // El backend está hecho para que se registre con todas las variables, pero el frontend está hecho para que se registre solamente el username y el password
                    val user = User(
                        username = signupUsername.text.toString(),
                        password = signupPassword.text.toString(),
                        email = "Email",
                        name = "Name",
                        surname = "Surname",
                        admin = false,
                        baja = false
                    )


                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


                    val con = Retrofit.Builder().baseUrl(Routes.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()


                    val response = con.create(APIService::class.java).postRegister(user)


                    if (response.isSuccessful) {
                            val message = response.body()
                            if (message != null) {
                                Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT).show()


                                // Redirige a la pantalla de inicio de sesión
                                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish() // Cierra la actividad actual para que no pueda volver atrás desde la pantalla de inicio de sesión
                            } else {
                                showRegisterError()
                            }
                    } else {
                            showRegisterError()
                            println(response.errorBody()?.string())
                    }


                } catch (e: Exception) {
                        showRegisterError()
                        e.printStackTrace()
                }
            }
        }
    }


    private fun validateFields(): Boolean {
        val signupUser = signupUsername.text.toString()
        val signupPassword = signupPassword.text.toString()


        return if (signupUser.isNotEmpty() && signupPassword.isNotEmpty()) {
            true
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            false
        }
    }


    private fun showRegisterError() {
        Toast.makeText(this@SignupActivity, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
    }
}
