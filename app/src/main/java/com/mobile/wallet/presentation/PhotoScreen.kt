package com.mobile.wallet.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.wallet.R
import com.mobile.wallet.data.navigation.Screen
import com.mobile.wallet.data.photo.PhotoUIEvent
import com.mobile.wallet.data.photo.PhotoViewModel
import com.mobile.wallet.presentation.components.ButtonComponent
import com.mobile.wallet.presentation.components.HeadingTextComponent
import com.mobile.wallet.presentation.components.NormalTextComponent
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


@Composable
fun PhotoScreen(navController: NavHostController, photoViewModel: PhotoViewModel = viewModel()) {

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
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            NormalTextComponent(value = stringResource(id = R.string.taking_id))
            HeadingTextComponent(value = stringResource(id = R.string.register))
            Spacer(modifier = Modifier.height(20.dp))


            val context = LocalContext.current
            val file = context.createImageFile(photoViewModel.uIState.value.photoId)
            val uri = FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                context.packageName + ".provider", file
            )

            var capturedImageUri by remember {
                mutableStateOf<Uri>(Uri.EMPTY)
            }


            val cameraLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {

                    if (it) {
                        capturedImageUri = uri
                        photoViewModel.onEvent(PhotoUIEvent.PictureTaken)
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


            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                if (capturedImageUri.path?.isNotEmpty() == true) {
                    println("capturedImageUri:: ->" + capturedImageUri)
                    Image(
                        modifier = Modifier
                            .padding(16.dp, 8.dp),
                        painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
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

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.take_photo),
                    onButtonClicked = {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    isEnabled = true
                )

            }
        }
        if (photoViewModel.navigate.value) {
            navController.navigate(Screen.Success.id)
        }
    }
}

fun Context.createImageFile(uuid: String): File {

    val imageFileName = uuid + "_" + System.currentTimeMillis() + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )

    return image
}