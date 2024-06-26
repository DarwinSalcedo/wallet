package com.mobile.wallet.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.wallet.R
import com.mobile.wallet.domain.navigation.Screen
import com.mobile.wallet.domain.photo.PhotoUIEvent
import com.mobile.wallet.domain.photo.PhotoViewModel
import com.mobile.wallet.presentation.components.ButtonComponent
import com.mobile.wallet.presentation.components.HeadingTextComponent
import com.mobile.wallet.presentation.components.NormalTextComponent
import com.mobile.wallet.presentation.components.StepComponent
import java.io.File
import java.util.Objects


@Composable
fun PhotoScreen(navController: NavHostController, photoViewModel: PhotoViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Boolean>(false)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = it
            if (it) {
                photoViewModel.onEvent(PhotoUIEvent.PictureTaken(uri))
            }
        }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {


            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {


                StepComponent(value = "2")
                NormalTextComponent(value = stringResource(id = R.string.taking_id))
                HeadingTextComponent(value = stringResource(id = R.string.register))
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {

                    if (capturedImageUri) {
                        Image(
                            modifier = Modifier
                                .padding(16.dp, 8.dp),
                            painter = painterResource(id = R.drawable.baseline_assignment_turned_in_24),
                            contentDescription = null
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .padding(16.dp, 8.dp),
                            painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                            contentDescription = null
                        )
                    }
                    if (photoViewModel.progress.value) {
                        CircularProgressIndicator()
                    }
                    Spacer(modifier = Modifier.height(40.dp))

                    ButtonComponent(
                        value = stringResource(id = R.string.take_photo),
                        onButtonClicked = {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )

                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                        isEnabled = !photoViewModel.progress.value
                    )

                }

                if (photoViewModel.navigate.value) {
                    navController.navigate(Screen.Success.id)
                    photoViewModel.resetPostNavigation()
                }
            }
        }
    }
}

fun Context.createImageFile(): File {

    val imageFileName = "${System.currentTimeMillis()}"

    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}