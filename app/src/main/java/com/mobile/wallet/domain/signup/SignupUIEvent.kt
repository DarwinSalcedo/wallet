package com.mobile.wallet.domain.signup

sealed class SignupUIEvent{
    data class NameChanged(val name:String) : SignupUIEvent()
    data class SurnameChanged(val surname:String) : SignupUIEvent()
    data class EmailChanged(val email:String): SignupUIEvent()
    data class PasswordChanged(val password: String) : SignupUIEvent()
    object RegisterButtonClicked : SignupUIEvent()
}
