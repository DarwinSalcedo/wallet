package com.mobile.wallet.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mobile.wallet.R
import com.mobile.wallet.domain.navigation.Screen
import com.mobile.wallet.presentation.components.ButtonComponent
import com.mobile.wallet.presentation.components.DividerTextComponent
import com.mobile.wallet.presentation.components.HeadingTextComponent
import com.mobile.wallet.presentation.components.NormalTextComponent

@Composable
fun SuccessScreen(navController: NavHostController) {
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
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(20.dp))

                NormalTextComponent(value = stringResource(id = R.string.welcome_message))


                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login), onButtonClicked = {
                        navController.popBackStack(Screen.Login.id, false)
                    }, isEnabled = true
                )
            }

        }
        BackHandler(true) {}

    }


}