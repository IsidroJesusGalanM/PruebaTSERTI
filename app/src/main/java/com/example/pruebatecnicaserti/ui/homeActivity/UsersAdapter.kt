package com.example.pruebatecnicaserti.ui.homeActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebatecnicaserti.databinding.UserCardBinding
import com.example.pruebatecnicaserti.model.remote.users.UserData

class UsersAdapter(
    private val users: List<UserData>,
    private val onItemClick: (UserData) -> Unit
): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: UserCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvUserName.text = user.first_name
        holder.binding.tvLastName.text = user.last_name
        holder.binding.tvEmail.text = user.email
        holder.binding.tvUserID.text = "#${user.id}"
        Glide
            .with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.binding.ivUser)
        holder.binding.root.setOnClickListener {
            onItemClick(user)
        }
    }




}