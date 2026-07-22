package com.example.ewalletapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewalletapp.api.AccountApiService
import com.example.ewalletapp.model.Account
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

    init {
        fetchMyAccount()
    }

     fun fetchMyAccount() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                delay(1000)
                val response = api.getMyAccount()
                account.value = response
            } catch (e: Exception) {
                errorMessage.value = "Bakiye yüklenemedi: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}