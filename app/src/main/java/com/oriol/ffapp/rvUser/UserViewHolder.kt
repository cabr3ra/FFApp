package com.oriol.ffapp.rvUser

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val username: TextView = itemView.findViewById(R.id.tv_rv_username)
    val password: TextView = itemView.findViewById(R.id.tv_rv_password)
    val name: TextView = itemView.findViewById(R.id.tv_rv_name)
    val surname: TextView = itemView.findViewById(R.id.tv_rv_surname)
    val email: TextView = itemView.findViewById(R.id.tv_rv_email)

}
