package com.mobile.wallet.domain.signup

import com.mobile.wallet.domain.login.EditTextState

data class SignupUIState(
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",

    var nameError: EditTextState = EditTextState.Init,
    var lastNameError: EditTextState = EditTextState.Init,
    var emailError: EditTextState = EditTextState.Init,
    var passwordError: EditTextState = EditTextState.Init,
)
