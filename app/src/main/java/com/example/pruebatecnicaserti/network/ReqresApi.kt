package com.example.pruebatecnicaserti.network

import com.example.pruebatecnicaserti.model.remote.login.LoginRequest
import com.example.pruebatecnicaserti.model.remote.login.LoginResponse
import com.example.pruebatecnicaserti.model.remote.register.RegisterRequest
import com.example.pruebatecnicaserti.model.remote.register.RegisterResponse
import com.example.pruebatecnicaserti.model.remote.singleUser.SingleUser
import com.example.pruebatecnicaserti.model.remote.users.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface ReqresApi {
    @Headers("x-api-key: reqres-free-v1")
    @GET("users")
    suspend fun getUsers(): Response<Users>

    @Headers("x-api-key: reqres-free-v1")
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @Headers("x-api-key: reqres-free-v1")
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @Headers("x-api-key: reqres-free-v1")
    @GET("users/{id}")
    suspend fun getSingleUser(@Path("id") id: Int): Response<SingleUser>


}