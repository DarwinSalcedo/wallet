@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.mobile.wallet.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.wallet.R
import com.mobile.wallet.domain.login.EditTextState
import com.mobile.wallet.utils.categories
import com.mobile.wallet.utils.positiveCategories
import com.mobile.wallet.utils.toCurrency

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.text),
        textAlign = TextAlign.Center
    )
}

@Composable
fun StepComponent(value: String) {
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
            text = value,
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeadingTextComponent(value: String, modifier: Modifier = Modifier.fillMaxWidth()) {
    Text(
        text = value,
        modifier = modifier
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.text),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CategoryComponent(
    value: Pair<String, ImageVector>,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        Modifier
            .padding(5.dp)
            .clickable { onClick.invoke() },
    ) {
        Row(Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = value.second,
                contentDescription = "category ${value.first}",
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = value.first,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
                    fontStyle = if (isSelected) FontStyle.Italic else FontStyle.Normal
                ), color = colorResource(id = R.color.text),
                textAlign = TextAlign.Center
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextChanged: (String) -> Unit,
    errorStatus: EditTextState = EditTextState.Init
) {

    val textValue = rememberSaveable {
        mutableStateOf("")
    }

    val errorValue = rememberSaveable {
        mutableStateOf(false)
    }

    errorValue.value = (errorStatus == EditTextState.Error)

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.Gray,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = errorValue.value
    )
}


@Composable
fun PasswordTextFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: EditTextState = EditTextState.Init
) {

    val localFocusManager = LocalFocusManager.current

    val password = rememberSaveable {
        mutableStateOf("")
    }

    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Gray,
            focusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.Gray,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                if (passwordVisible.value) {
                    Icon(
                        painterResource(id = R.drawable.baseline_face_24),
                        stringResource(id = R.string.hide)
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.baseline_face_retouching_off_24),
                        stringResource(id = R.string.show)
                    )
                }
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = (errorStatus == EditTextState.Error)
    )
}

@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    val localFocusManager = LocalFocusManager.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
            localFocusManager.clearFocus(true)
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Black),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}


@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText =
        if (tryingToLogin) "Already have an account? " else "Don’t have an account yet? "
    val loginText = if (tryingToLogin) "Login" else "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Gray)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }

        },
    )
}

@Composable
fun DisappearingMessage(
    message: String,
    callback: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(horizontal = 14.dp),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(Modifier.clickable {
                callback.invoke()
            }
            ) {
                Icon(Icons.Filled.Cancel, contentDescription = "Add item")
            }
        }
    }


}

@Composable
fun BalanceCard(
    value: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    imageVector = Icons.Filled.Paid,
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Balance",
                )
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.End

            )
        }
    }
}

@Composable
fun TransactionCard(
    uuid: String,
    value: Double,
    category: String,
    note: String,
    date: String,

    ) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        TransactionInfoRow(
            category,
            date,
            value,
            note
        )
    }
}

@Composable
fun TransactionInfoRow(category: String, date: String, value: Double, note: String) {
    val categoryElement = categories.firstOrNull { it.first == category }
    val plusColor = positiveCategories.contains(categoryElement?.first)

    Column(Modifier.padding(16.dp)) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column(
                    modifier = Modifier.weight(6f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = categoryElement?.second
                                ?: Icons.Filled.Lightbulb,
                            contentDescription = "",
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = category,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    if (note.isNotEmpty()) {
                        Text(
                            text = note,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                }
                Text(
                    modifier = Modifier.weight(4f),
                    text = value.toCurrency(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.End,
                    color = if (plusColor) Color.Green else Color.Black

                )
            }
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

    }
}








