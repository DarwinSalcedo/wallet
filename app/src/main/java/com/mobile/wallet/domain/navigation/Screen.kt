package com.mobile.wallet.domain.navigation

sealed class Screen(val id : String) {
    object SignUp : Screen("signup")
    object Photo : Screen("photo")
    object Success : Screen("success")
    object Login : Screen("login")
    object Home : Screen("home")
    object Transaction : Screen("transaction")
}
