package com.example.ewalletapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewalletapp.api.TransactionApiService
import com.example.ewalletapp.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransferViewModel : ViewModel() {

    private val api = RetrofitClient.instance.create(TransactionApiService::class.java)

    var isLoading = mutableStateOf(false)
        private set

    var transferStatus = mutableStateOf<String?>(null)
        private set

    fun sendMoney(senderId: Long, receiverId: Long, amount: Double) {
        viewModelScope.launch {
            isLoading.value = true
            transferStatus.value = null
            try {
                delay(1000)
                val response = api.transferMoney(senderId, receiverId, amount)

                if (response.isSuccessful) {
                    transferStatus.value = "Transfer Başarılı! 🎉"
                } else {
                    transferStatus.value = "İşlem Reddedildi: ${response.code()}"
                }
            } catch (e: Exception) {
                transferStatus.value = "Bağlantı Hatası: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun resetStatus() {
        transferStatus.value = null
    }
}