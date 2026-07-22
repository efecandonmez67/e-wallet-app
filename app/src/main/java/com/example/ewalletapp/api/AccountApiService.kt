package com.example.ewalletapp.api

import com.example.ewalletapp.model.Account
import retrofit2.http.GET

interface AccountApiService {
    @GET("/api/v1/accounts/me")
    suspend fun getMyAccount(): Account
}