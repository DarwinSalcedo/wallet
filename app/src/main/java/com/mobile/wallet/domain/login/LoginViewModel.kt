package com.mobile.wallet.domain.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import com.mobile.wallet.domain.rules.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: FirebaseRepository) : ViewModel() {

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    var loginInProgress = mutableStateOf(false)

    var navigate = mutableStateOf(false)


    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = if (emailResult.status) EditTextState.Success else EditTextState.Error,
            passwordError = if (passwordResult.status) EditTextState.Success else EditTextState.Error
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    private fun login() {
        viewModelScope.launch {


            loginInProgress.value = true
            val email = loginUIState.value.email
            val password = loginUIState.value.password

            repository.login(email, password).collect {
                when (val result = it) {

                    is Result.Failure -> {
                        _errorMessage.value =
                            result.error.localizedMessage ?: "Unexpected Error"
                        loginInProgress.value = false
                    }

                    is Result.Success -> {
                        _errorMessage.value = ""
                        navigate.value = true
                        loginInProgress.value = false
                    }
                }
            }


        }
    }

    fun resetMessage() {
        _errorMessage.value = ""
    }

}


