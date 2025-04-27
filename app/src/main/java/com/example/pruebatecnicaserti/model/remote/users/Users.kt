package com.example.pruebatecnicaserti.model.remote.users

data class Users(
    val `data`: List<UserData>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)