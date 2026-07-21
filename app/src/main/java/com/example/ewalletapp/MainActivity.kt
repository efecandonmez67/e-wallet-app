package com.example.ewalletapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ewalletapp.network.TokenManager
import com.example.ewalletapp.screens.HomeScreen
import com.example.ewalletapp.screens.LoginScreen
import com.example.ewalletapp.ui.theme.EWalletAppTheme
import com.example.ewalletapp.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        setContent {
            EWalletAppTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {

                    composable("login") {
                        LoginScreen(viewModel = authViewModel, navController = navController)
                    }

                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }
}