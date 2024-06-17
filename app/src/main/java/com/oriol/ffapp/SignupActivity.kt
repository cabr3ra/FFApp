package com.oriol.ffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.oriol.ffapp.model.User
import com.oriol.ffapp.server.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            postUserRegister()
        }

        val tvLoginRedirect = findViewById<TextView>(R.id.loginRedirect)
        tvLoginRedirect.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun postUserRegister() {
        val usernameText = signupUsername.text.toString()
        val passwordText = signupPassword.text.toString()


        if (validateFields(usernameText, passwordText)) {
            val user = User(
                usernameUser = usernameText,
                passwordUser = passwordText,

            )

            val call = RetrofitClient.apiService.postRegister(user)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        println("Solicitud POST exitosa $usernameText $passwordText")
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        println("Error en la solicitud POST: ${response.code()}")
                        Toast.makeText(this@SignupActivity, "Error al registrarse: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    println("Error en la solicitud POST: ${t.message}")
                    Toast.makeText(this@SignupActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
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

