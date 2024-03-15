package com.mobile.wallet.domain.signup

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import com.mobile.wallet.data.repository.auth.FirebaseRepositoryImpl
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.domain.login.EditTextState
import com.mobile.wallet.domain.models.User
import com.mobile.wallet.domain.rules.Validator
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SignupViewModel : ViewModel() {

    private val TAG = SignupViewModel::class.simpleName

    var registrationUIState = mutableStateOf(SignupUIState())

    var allValidationsPassed = mutableStateOf(false)

    var progress = mutableStateOf(false)

    var navigate = mutableStateOf(false)

    var errorMessage = mutableStateOf("", neverEqualPolicy())

    private var repository: FirebaseRepository = FirebaseRepositoryImpl()

    private var job: Job? = null

    fun onEvent(event: SignupUIEvent) {
        when (event) {
            is SignupUIEvent.NameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    name = event.name
                )
            }

            is SignupUIEvent.SurnameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    surname = event.surname
                )
            }

            is SignupUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }


            is SignupUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }

            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
            }

        }
        validateDataWithRules()
    }


    private fun signUp() {
        val user = User(
            name = registrationUIState.value.name,
            lastName = registrationUIState.value.surname,
            email = registrationUIState.value.email,
            password = registrationUIState.value.password,
        )
        createUserInFirebase(
            user
        )
    }

    private fun validateDataWithRules() {
        val nameResult = Validator.validateName(
            value = registrationUIState.value.name
        )

        val surnameResult = Validator.validateName(
            value = registrationUIState.value.surname
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        registrationUIState.value = registrationUIState.value.copy(
            nameError = if (nameResult.status) EditTextState.Success else EditTextState.Error,
            lastNameError = if (surnameResult.status) EditTextState.Success else EditTextState.Error,
            emailError = if (emailResult.status) EditTextState.Success else EditTextState.Error,
            passwordError = if (passwordResult.status) EditTextState.Success else EditTextState.Error
        )


        allValidationsPassed.value = nameResult.status && surnameResult.status &&
                emailResult.status && passwordResult.status

    }


    private fun createUserInFirebase(user: User) {
        job?.cancel()
        job =   viewModelScope.launch {
            progress.value = true

            repository.createAccount(user).collect {

                when (val result = it) {
                    is Result.Failure -> {
                        errorMessage.value = result.error.localizedMessage.toString()
                        progress.value = false
                    }

                    is Result.Success -> {
                        errorMessage.value = ""
                        progress.value = true
                        navigate.value = true
                    }
                }
            }
        }

    }

    fun resetPostNavigation() {
        this.navigate.value = false
        progress.value = false
    }


}
