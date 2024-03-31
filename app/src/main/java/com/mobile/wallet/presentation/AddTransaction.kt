package com.mobile.wallet.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mobile.wallet.presentation.components.CategoryComponent
import com.mobile.wallet.utils.categories

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun AddTransaction(
    callback: () -> Unit,
    //viewmodel
) {

    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {

                val itemList = remember { mutableStateOf(categories) }

                Column {

                    Button(
                        onClick = {
                            //todo save trx
                        },
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add item")
                    }

                    val textValue = rememberSaveable {
                        mutableStateOf("")
                    }

                    val errorValue = rememberSaveable {
                        mutableStateOf(false)
                    }


                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(text = "labelValue") },
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
                            //  onTextChanged(it)
                            //todo select amount
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Add, contentDescription = "Add item")
                        },
                        isError = errorValue.value
                    )

                    val columns = GridCells.Adaptive(130.dp)

                    LazyVerticalGrid(columns = columns) {
                        items(itemList.value.size) { item ->
                                //todo clicked select category
                                CategoryComponent(value = itemList.value.get(item))


                        }
                    }
                    Spacer(modifier = Modifier.height(65.dp))
                }
            }
        }, onDismissRequest = {
            callback.invoke()
        })


}