package com.mobile.wallet.data.signup

data class SignupUIState(
    var name: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",

    var nameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
)
