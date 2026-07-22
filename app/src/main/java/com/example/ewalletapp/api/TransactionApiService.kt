package com.example.ewalletapp.api

import com.example.ewalletapp.model.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApiService {
    @POST("/api/v1/transaction/transfer")
    suspend fun transferMoney(
        @Query("senderId") senderId: Long,
        @Query("receiverId") receiverId: Long,
        @Query("amount") amount: Double
    ): Response<Void>

    @GET("/api/v1/transaction/history/{accountId}")
    suspend fun getTransactionHistory(@Path("accountId") accountId: Long): Response<List<TransactionResponse>>


}