package com.mobile.wallet.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.wallet.R
import com.mobile.wallet.domain.navigation.Screen
import com.mobile.wallet.domain.signup.SignupUIEvent
import com.mobile.wallet.domain.signup.SignupViewModel
import com.mobile.wallet.presentation.components.ButtonComponent
import com.mobile.wallet.presentation.components.ClickableLoginTextComponent
import com.mobile.wallet.presentation.components.DisappearingMessage
import com.mobile.wallet.presentation.components.DividerTextComponent
import com.mobile.wallet.presentation.components.HeadingTextComponent
import com.mobile.wallet.presentation.components.NormalTextComponent
import com.mobile.wallet.presentation.components.PasswordTextFieldComponent
import com.mobile.wallet.presentation.components.StepComponent
import com.mobile.wallet.presentation.components.TextFieldComponent

@Composable
fun SignUpScreen(navController: NavHostController, signupViewModel: SignupViewModel = viewModel()) {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {


            Column(modifier = Modifier.fillMaxSize()) {

                StepComponent(value = "1")
                NormalTextComponent(value = stringResource(id = R.string.lets_go))
                HeadingTextComponent(value = stringResource(id = R.string.register))
                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.name_register),
                    painterResource(id = R.drawable.baseline_account_circle_24),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.NameChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.nameError
                )

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.surname_register),
                    painterResource = painterResource(id = R.drawable.baseline_account_circle_24),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.SurnameChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.lastNameError
                )

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.baseline_alternate_email_24),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.emailError
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.baseline_lock_outline_24),
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (signupViewModel.errorMessage.value.isNotEmpty())
                    DisappearingMessage(signupViewModel.errorMessage.value) {
                        signupViewModel.errorMessage.value = ""
                    }

                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.photo_step), onButtonClicked = {
                        signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                    }, isEnabled = signupViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    navController.popBackStack()
                })
            }

        }

        if (signupViewModel.progress.value) {
            CircularProgressIndicator()
        }

        if (signupViewModel.navigate.value) {
            navController.navigate(Screen.Photo.id)
            signupViewModel.resetPostNavigation()
        }

    }


}