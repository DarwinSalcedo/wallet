package com.mobile.wallet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.wallet.domain.main.MainViewModel
import com.mobile.wallet.domain.navigation.Screen
import com.mobile.wallet.ui.theme.WalletappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                WalletappTheme {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    view: MainViewModel = viewModel()
) {

    NavHost(
        navController = navController,
        startDestination = if (view.inSession) Screen.Home.id else Screen.Login.id,
    ) {
        composable(Screen.Login.id) { LoginScreen(navController) }
        composable(Screen.SignUp.id) { SignUpScreen(navController) }
        composable(Screen.Photo.id) { PhotoScreen(navController) }
        composable(Screen.Success.id) { SuccessScreen(navController) }
        composable(Screen.Home.id) { HomeScreen(navController) }
    }
}

