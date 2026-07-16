package com.example.ewalletapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewalletapp.api.AuthApiService
import com.example.ewalletapp.model.LoginRequest
import com.example.ewalletapp.network.RetrofitClient
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val api = RetrofitClient.instance.create(AuthApiService::class.java)

    var loginResult = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val request = LoginRequest(username, password)
                val response = api.login(request)
                loginResult.value = response
            } catch (e: Exception) {
                loginResult.value = "Hata: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}