package com.example.ewalletapp.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionApiService {
    @POST("/api/v1/transaction/transfer")
    suspend fun transferMoney(
        @Query("senderId") senderId: Long,
        @Query("receiverId") receiverId: Long,
        @Query("amount") amount: Double
    ): Response<Void>
}