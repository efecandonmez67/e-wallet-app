package com.example.ewalletapp.model

data class TransactionResponse(
    val id: Long,
    val senderAccountId: Long,
    val receiverAccountId: Long,
    val amount: Double,
    val transactionDate: String?
)