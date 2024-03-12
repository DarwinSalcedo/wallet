package com.mobile.wallet.data.navigation

sealed class Screen(val id : String) {
    object SignUp : Screen("signup")
    object Photo : Screen("photo")
    object Success : Screen("success")
    object Login : Screen("login")
    object Home : Screen("home")
}
