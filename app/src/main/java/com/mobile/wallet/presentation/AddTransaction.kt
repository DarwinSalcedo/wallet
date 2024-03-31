package com.mobile.wallet.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile.wallet.R
import com.mobile.wallet.domain.home.TransactionViewModel
import com.mobile.wallet.presentation.components.CategoryComponent
import com.mobile.wallet.presentation.components.CurrencyAmountInputVisualTransformation
import com.mobile.wallet.presentation.components.DisappearingMessage
import com.mobile.wallet.presentation.components.HeadingTextComponent

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun AddTransaction(
    callback: () -> Unit,
    success: () -> Unit,
    viewmodel: TransactionViewModel = viewModel(),
) {

    if (viewmodel.successExecution.value) {
        viewmodel.resetSuccessExecution()
        success.invoke()
        callback.invoke()
    }
    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {


                Column {
                    val list = remember { mutableStateOf(viewmodel.categoryList) }

                    val selectedIndex =
                        remember { mutableIntStateOf(-1) }

                    val amountValue = rememberSaveable {
                        mutableStateOf("")
                    }

                    Row(
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HeadingTextComponent(
                            value = "Add Transaction",
                            modifier = Modifier.wrapContentWidth()
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            onClick = {
                                viewmodel.validate(selectedIndex.intValue, amountValue.value)
                            }
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = "Add item")
                        }
                    }
                    if (viewmodel.error.value.isNotEmpty()) {
                        DisappearingMessage(message = viewmodel.error.value) { viewmodel.resetError() }
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(text = "Amount") },
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Transparent,
                            focusedLabelColor = Color.Gray,
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        singleLine = true,
                        value = amountValue.value,
                        onValueChange = {
                            amountValue.value = if (it.startsWith("0")) {
                                ""
                            } else {
                                it
                            }
                        },
                        textStyle = MaterialTheme.typography.headlineLarge,
                        visualTransformation = CurrencyAmountInputVisualTransformation(false),
                    )

                    val columns = GridCells.Fixed(2)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Chose a category",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(),
                        style = TextStyle(
                            fontSize = 16.sp,
                        ), color = colorResource(id = R.color.text),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(columns = columns) {
                        items(list.value.size) { item ->
                            val element = list.value[item]
                            val isSelected =
                                selectedIndex.value == list.value.indexOf(element)

                            CategoryComponent(
                                value = element,
                                isSelected = isSelected
                            ) {
                                selectedIndex.value = list.value.indexOf(element)
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(65.dp))
                }
            }
        }, onDismissRequest = {
            callback.invoke()
        })


}