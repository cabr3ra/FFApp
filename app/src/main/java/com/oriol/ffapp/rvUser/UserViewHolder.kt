package com.oriol.ffapp.rvUser

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.User

class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tv_rv_username = view.findViewById<TextView>(R.id.tv_rv_username)
    val tv_rv_password = view.findViewById<TextView>(R.id.tv_rv_password)
    val tv_rv_name = view.findViewById<TextView>(R.id.tv_rv_name)
    val tv_rv_surname = view.findViewById<TextView>(R.id.tv_rv_surname)
    val tv_rv_email = view.findViewById<TextView>(R.id.tv_rv_email)

    fun printUser(user: User){
        tv_rv_username.text = user.usernameUser
        tv_rv_password.text = user.passwordUser
        tv_rv_name.text = user.nameUser
        tv_rv_surname.text = user.surnameUser
        tv_rv_email.text = user.emailUser
    }
}
