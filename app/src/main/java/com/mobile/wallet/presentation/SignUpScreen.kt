package com.mobile.wallet.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.wallet.R
import com.mobile.wallet.data.login.EditTextState
import com.mobile.wallet.data.navigation.Screen
import com.mobile.wallet.data.signup.SignupUIEvent
import com.mobile.wallet.data.signup.SignupViewModel
import com.mobile.wallet.presentation.components.ButtonComponent
import com.mobile.wallet.presentation.components.ClickableLoginTextComponent
import com.mobile.wallet.presentation.components.DividerTextComponent
import com.mobile.wallet.presentation.components.HeadingTextComponent
import com.mobile.wallet.presentation.components.NormalTextComponent
import com.mobile.wallet.presentation.components.PasswordTextFieldComponent
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

                Box(
                    modifier = Modifier
                        .width(38.dp)
                        .height(38.dp)
                        .background(
                            brush = Brush.horizontalGradient(listOf(Color.Gray, Color.DarkGray)),
                            shape = RoundedCornerShape(50.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1",
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                NormalTextComponent(value = stringResource(id = R.string.lets_go))
                HeadingTextComponent(value = stringResource(id = R.string.register))
                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.name_register),
                    painterResource(id = R.drawable.baseline_account_circle_24),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.NameChanged(it))
                    },
                    errorStatus = if (signupViewModel.registrationUIState.value.nameError) EditTextState.Error else EditTextState.Init
                )

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.surname_register),
                    painterResource = painterResource(id = R.drawable.baseline_account_circle_24),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.SurnameChanged(it))
                    },
                    errorStatus = if (signupViewModel.registrationUIState.value.lastNameError) EditTextState.Error else EditTextState.Init
                )

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.baseline_alternate_email_24),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                    },
                    errorStatus = if (signupViewModel.registrationUIState.value.emailError) EditTextState.Error else EditTextState.Init
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.baseline_lock_outline_24),
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = if (signupViewModel.registrationUIState.value.passwordError) EditTextState.Error else EditTextState.Init
                )

                Spacer(modifier = Modifier.height(40.dp))

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
            navController.currentBackStackEntry?.savedStateHandle?.set("uuid", signupViewModel.uuid.value)
            navController.navigate(Screen.Photo.id,)
            signupViewModel.resetPostNavigation()
        }

    }


}