package com.oriol.ffapp.rvUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.User

class UserRvAdapter(private val users:List<User>)
    : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)

        return UserViewHolder(layoutInflate.inflate(R.layout.rv_user_layout, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.printUser(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}