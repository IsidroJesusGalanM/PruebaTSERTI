package com.example.pruebatecnicaserti.repository

import com.example.pruebatecnicaserti.model.remote.singleUser.SingleUser
import com.example.pruebatecnicaserti.model.remote.users.Users
import com.example.pruebatecnicaserti.network.ReqresApi
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val reqresApi: ReqresApi
) {
    suspend fun getUsers(): Result<Users> {
        return try {
            val response = reqresApi.getUsers()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getSingleUser(id: Int): Result<SingleUser> {
        return try {
            val response = reqresApi.getSingleUser(id)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response}"))
            }

        }catch (e: Exception){
            Result.failure(e)
        }
    }
}