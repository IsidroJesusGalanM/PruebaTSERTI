package com.example.pruebatecnicaserti.repository

import com.example.pruebatecnicaserti.model.remote.register.RegisterErrorResponse
import com.example.pruebatecnicaserti.model.remote.register.RegisterRequest
import com.example.pruebatecnicaserti.model.remote.register.RegisterResponse
import com.example.pruebatecnicaserti.network.ReqresApi
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val api: ReqresApi
) {
    suspend fun registerUser(request: RegisterRequest): Result<RegisterResponse>{
        return try {
            val response = api.registerUser(request)

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

    private fun parseError(response: Response<RegisterResponse>): RegisterErrorResponse? {
        return try {
            val errorBody = response.errorBody()?.string()
            val gson = Gson()
            gson.fromJson(errorBody, RegisterErrorResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}