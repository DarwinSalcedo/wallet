package com.mobile.wallet.data.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mobile.wallet.data.User
import com.mobile.wallet.data.rules.Validator


class SignupViewModel : ViewModel() {

    private val TAG = SignupViewModel::class.simpleName

    var registrationUIState = mutableStateOf(SignupUIState())

    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    var navigate = mutableStateOf(false)

    fun onEvent(event: SignupUIEvent) {
        when (event) {
            is SignupUIEvent.NameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    name = event.name
                )
            }

            is SignupUIEvent.SurnameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.surname
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
            lastName = registrationUIState.value.lastName,
            email = registrationUIState.value.email,
            password = registrationUIState.value.password,
        )
        createUserInFirebase(
            user
        )
    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.name
        )

        val lNameResult = Validator.validateLastName(
            lName = registrationUIState.value.lastName
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        registrationUIState.value = registrationUIState.value.copy(
            nameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )


        allValidationsPassed.value = fNameResult.status && lNameResult.status &&
                emailResult.status && passwordResult.status

    }


    private fun createUserInFirebase(user: User) {

        signUpInProgress.value = true
        navigate.value = true

    }


}
