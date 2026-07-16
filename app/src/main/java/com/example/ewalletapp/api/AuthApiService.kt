package com.example.ewalletapp.api

import com.example.ewalletapp.model.LoginRequest
import com.example.ewalletapp.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/api/v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): String

    @POST("/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): String

}