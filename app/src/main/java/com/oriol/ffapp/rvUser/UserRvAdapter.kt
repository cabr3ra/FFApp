package com.oriol.ffapp.rvUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.model.User
import com.oriol.ffapp.R

class UserRvAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_user_layout, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.username.text = currentUser.usernameUser
        holder.password.text = currentUser.passwordUser
        holder.name.text = currentUser.nameUser
        holder.surname.text = currentUser.surnameUser
        holder.email.text = currentUser.emailUser
        holder.admin.text = currentUser.adminUser.toString()
        holder.baja.text = currentUser.bajaUser.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

