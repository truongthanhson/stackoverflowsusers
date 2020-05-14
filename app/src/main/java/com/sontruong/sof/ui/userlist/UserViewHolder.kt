package com.sontruong.sof.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sontruong.sof.R
import com.sontruong.sof.data.remote.model.User

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userNameTv: TextView = itemView.findViewById(R.id.name)
    private val avatar: ImageView = itemView.findViewById(R.id.avatar)

    fun bind(user: User?) {
        Glide.with(itemView.context).load(user?.profileImage).into(avatar)
        userNameTv.text = user?.displayName
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_user, parent, false)
            return UserViewHolder(view)
        }
    }
}