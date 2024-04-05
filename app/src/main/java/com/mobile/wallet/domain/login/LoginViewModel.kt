package com.mobile.wallet.domain.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.core.DispatcherProvider
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import com.mobile.wallet.domain.rules.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: FirebaseRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var uiState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    var loginInProgress = mutableStateOf(false)

    var navigate = mutableStateOf(false)


    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                uiState.value = uiState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                uiState.value = uiState.value.copy(
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
            email = uiState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = uiState.value.password
        )

        uiState.value = uiState.value.copy(
            emailError = if (emailResult.status) EditTextState.Success else EditTextState.Error,
            passwordError = if (passwordResult.status) EditTextState.Success else EditTextState.Error
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    private fun login() {
        viewModelScope.launch((dispatcherProvider.io)) {

            loginInProgress.value = true
            val email = uiState.value.email
            val password = uiState.value.password

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


