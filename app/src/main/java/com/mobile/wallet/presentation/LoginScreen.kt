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
import com.mobile.wallet.domain.login.LoginUIEvent
import com.mobile.wallet.domain.login.LoginViewModel
import com.mobile.wallet.domain.navigation.Screen
import com.mobile.wallet.presentation.components.ButtonComponent
import com.mobile.wallet.presentation.components.ClickableLoginTextComponent
import com.mobile.wallet.presentation.components.DisappearingMessage
import com.mobile.wallet.presentation.components.DividerTextComponent
import com.mobile.wallet.presentation.components.HeadingTextComponent
import com.mobile.wallet.presentation.components.PasswordTextFieldComponent
import com.mobile.wallet.presentation.components.TextFieldComponent

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {


    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                HeadingTextComponent(value = stringResource(id = R.string.title))
                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource(id = R.drawable.baseline_alternate_email_24),
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.baseline_lock_outline_24),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )


                Spacer(modifier = Modifier.height(10.dp))
                if(loginViewModel.errorMessage.value.isNotEmpty())
                    DisappearingMessage(loginViewModel.errorMessage.value)



                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login), onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    }, isEnabled = loginViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    navController.navigate(Screen.SignUp.id)
                })


            }



            if (loginViewModel.navigate.value) {
                navController.navigate(Screen.Home.id)
            }
        }

        if (loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }
}