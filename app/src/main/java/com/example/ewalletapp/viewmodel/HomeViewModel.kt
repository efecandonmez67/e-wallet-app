package com.example.ewalletapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewalletapp.api.AccountApiService
import com.example.ewalletapp.api.TransactionApiService
import com.example.ewalletapp.model.Account
import com.example.ewalletapp.model.TransactionResponse
import com.example.ewalletapp.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val api = RetrofitClient.instance.create(AccountApiService::class.java)

    var account = mutableStateOf<Account?>(null)
        private set

    var isLoading = mutableStateOf(true)
        private set

    var errorMessage = mutableStateOf("")
        private set

    var transactionHistory = mutableStateOf<List<TransactionResponse>>(emptyList())
        private set

    var isHistoryLoading = mutableStateOf(false)
        private set

    init {
        fetchMyAccount()
    }

    fun fetchMyAccount() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val api = RetrofitClient.instance.create(AccountApiService::class.java)
                val response = api.getMyAccount()

                if (response.isSuccessful) {
                    account.value = response.body()

                    account.value?.id?.let { fetchTransactionHistory(it) }

                } else {
                    errorMessage.value = "Hesap bulunamadı: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Hata: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchTransactionHistory(accountId: Long) {
        viewModelScope.launch {
            isHistoryLoading.value = true
            try {
                val api = RetrofitClient.instance.create(TransactionApiService::class.java)
                val response = api.getTransactionHistory(accountId)

                if (response.isSuccessful) {
                    transactionHistory.value = response.body() ?: emptyList()
                } else {
                    println("Geçmiş çekilemedi: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Ağ hatası (History): ${e.message}")
            } finally {
                isHistoryLoading.value = false
            }
        }
    }



}