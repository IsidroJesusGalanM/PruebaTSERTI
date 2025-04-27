package com.example.pruebatecnicaserti.repository

import com.example.pruebatecnicaserti.model.remote.login.LoginErrorResponse
import com.example.pruebatecnicaserti.model.remote.login.LoginRequest
import com.example.pruebatecnicaserti.model.remote.login.LoginResponse
import com.example.pruebatecnicaserti.network.ReqresApi
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: ReqresApi,
){
    suspend fun loginUser(request: LoginRequest): Result<LoginResponse>{
        return try {
            val response = api.login(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorResponse = parseError(response)
                Result.failure(Exception(errorResponse?.error ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseError(response: Response<LoginResponse>): LoginErrorResponse? {
        return try {
            val errorBody = response.errorBody()?.string()
            val gson = Gson()
            gson.fromJson(errorBody, LoginErrorResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }

}