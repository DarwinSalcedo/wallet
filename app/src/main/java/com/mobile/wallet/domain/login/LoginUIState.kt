package com.mobile.wallet.domain.login

data class LoginUIState(
    var email: String = "",
    var password: String = "",

    var emailError: EditTextState = EditTextState.Init,
    var passwordError: EditTextState = EditTextState.Init
)

sealed class EditTextState() {
    object Init : EditTextState()
    object Success : EditTextState()
    object Error : EditTextState()

    fun isValid(): Boolean {
        println("dsad::" + this.toString())
        return (this is Success)

    }
}