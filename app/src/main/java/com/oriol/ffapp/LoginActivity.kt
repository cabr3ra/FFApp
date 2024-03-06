package com.oriol.ffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var loginUsername : EditText
    private lateinit var loginPassword : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginUsername = findViewById(R.id.loginUsername)
        loginPassword = findViewById(R.id.loginPassword)

        val btnLogin = findViewById<Button>(R.id.loginButton)
        btnLogin.setOnClickListener{
                postUserLogin(it)
        }

        val tvSignupRedirect = findViewById<TextView>(R.id.signupRedirect)
        tvSignupRedirect.setOnClickListener{
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    //Passa a la siguiente pantalla una vez se introduzca el usuario y el pasword
    fun openUserActivity(view: View) {
        if (validateFields()) {
            postUserLogin(view)
        }
    }

    fun postUserLogin(view: View) {
        if (validateFields()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                    val con = Retrofit.Builder().baseUrl(Routes.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()

                    val response = con.create(APIService::class.java).getLogin(
                        loginUsername.text.toString(),
                        loginPassword.text.toString()
                    )

                    if (response.isSuccessful) {
                        val usuario = response.body()
                        if (usuario != null) {
                                val intent = Intent(this@LoginActivity, Menu::class.java)
                                intent.putExtra("USERNAME_PARAMETRE", loginUsername.text.toString())
                                startActivity(intent)
                        } else {
                                showLoginError()
                        }
                    } else {
                            showLoginError()
                            println(response.errorBody()?.string())
                    }
                } catch (e: Exception) {
                        showLoginError()
                        e.printStackTrace()
                }
            }
        }
    }

    // Función para verificar si las credenciales son correctas
    private fun validateFields(): Boolean {
        val loginUser = loginUsername.text.toString()
        val loginPassword = loginPassword.text.toString()

        if (loginUser.isNotEmpty() && loginPassword.isNotEmpty()) {
            return true
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }
    }
    private fun showLoginError() {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
        }
    }
}
