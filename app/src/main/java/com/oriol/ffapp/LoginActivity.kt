package com.oriol.ffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.oriol.ffapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.loginButton)
        btnLogin.setOnClickListener{
            val intent = Intent(this@LoginActivity, Menu::class.java)
            startActivity(intent)
        }

    }

    //Passa a la siguiente pantalla una vez se introduzca el usuario y el pasword
    fun openUserActivity(view: View) {
        val input = findViewById<TextView>(R.id.loginUsername)
        val username = input.text;

        if(username.isNotEmpty()) {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("NOM_PARAMETRE", username)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Indica un username", Toast.LENGTH_SHORT).show()
        }
    }

    fun postUserLogin(view: View) {
        val inputUsername = findViewById<EditText>(R.id.loginUsername)
        val inputPassword = findViewById<EditText>(R.id.loginPassword)

        val userLogin = inputUsername.text.toString()
        val passwordLogin = inputPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val con = Retrofit.Builder().baseUrl(Rutes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val resposta = con.create(APIService::class.java).postLogin(
                "login",
                UserLogin(userLogin, passwordLogin)
            )
            if (resposta.isSuccessful) {
                println("Obtenim la resposta!");
                var user = resposta.body()?: User("", "","", "", "", false, false)
                println(resposta.body())
            } else {
                println(resposta.errorBody()?.string())
            }
        }
    }
}