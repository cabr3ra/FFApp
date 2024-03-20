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
import com.oriol.ffapp.server.RetrofitClient
import retrofit2.Call

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
        val usernameText = signupUsername.text.toString()
        val passwordText = signupPassword.text.toString()

        if (validateFields(usernameText, passwordText)) {
            val user = User(
                idUser = 0,
                username_user = usernameText,
                password_user = passwordText,
                email = "",
                name = "",
                surname = "",
                admin = false,
                baja = false
            )

            val call = RetrofitClient.apiService.postRegister(user)
            call.enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                    if (response.isSuccessful) {
                        // La solicitud fue exitosa
                        println("Solicitud POST exitosa"+user.username_user+" "+user.password_user)

                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        // La solicitud no fue exitosa
                        println("Error en la solicitud POST: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Se produjo un error de red u otro tipo de error
                    println("Error en la solicitud POST: ${t.message}")
                }
            })
        }
    }

    private fun validateFields(username: String, password: String): Boolean {
        return if (username.isNotEmpty() && password.isNotEmpty()) {
            true
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
