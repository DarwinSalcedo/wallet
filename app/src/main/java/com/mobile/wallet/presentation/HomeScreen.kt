package com.mobile.wallet.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.wallet.domain.home.HomeViewModel
import com.mobile.wallet.domain.models.Transaction
import com.mobile.wallet.domain.navigation.Screen
import com.mobile.wallet.presentation.components.BalanceCard
import com.mobile.wallet.presentation.components.TransactionCard
import com.mobile.wallet.utils.categories
import com.mobile.wallet.utils.toCurrency
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var clickedItem by remember { mutableStateOf(Transaction()) }

    if (uiState.navigate) {
        navController.navigate(Screen.Login.id)
    }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    val (showBottomSheet, setShowBottomSheet) = remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = showButton,
        enter = expandHorizontally(),
        exit = shrinkHorizontally(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.shapes.extraLarge,
                    ),
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }) {
                Icon(
                    imageVector = Icons.Filled.ArrowCircleUp,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 10.dp),
                colors = TopAppBarDefaults.largeTopAppBarColors(MaterialTheme.colorScheme.background),
                title = {
                    Text(
                        text = "Hello, " + (uiState.userName ?: "not found"),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                setShowBottomSheet(true)
            }) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    AnimatedVisibility(visible = !showButton) {
                        Text(
                            text = "Add Transaction".uppercase(Locale.getDefault()),
                            modifier = Modifier.padding(
                                start = 8.dp,
                            ),
                        )
                    }
                }
            }
        }
    ) {
        if (showBottomSheet) {
            AddTransaction { setShowBottomSheet(false) }
        }

        Column(modifier = Modifier.padding(it)) {
            BalanceCard(uiState.total.toCurrency())
            Text(
                text = "Transactions",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            if (uiState.loading) {
                CircularProgressIndicator()
            }
            if (uiState.errorMessage.isNotEmpty()) {
                Text(
                    text = uiState.errorMessage,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
            LazyColumn(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                ),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 16.dp
                ),
                state = listState
            ) {
                items(uiState.transactions) { transaction ->
                    Box(modifier = Modifier.clickable {
                        showDialog = true
                        clickedItem = transaction
                    }) {
                        TransactionCard(
                            uuid = transaction.uuid,
                            value = transaction.value,
                            category = transaction.category,
                            date = transaction.date.toString()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

       AnimatedVisibility(visible = showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Row {
                        Icon(
                            imageVector = categories.firstOrNull { it.first == clickedItem.category }?.second
                                ?: Icons.Filled.Lightbulb,
                            contentDescription = "",
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(clickedItem.category)
                    }


                },
                text = {
                    Column {

                        Text(
                            text = clickedItem.value.toCurrency(),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = clickedItem.date.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )

                    }
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Dismiss")
                    }
                }
            )
        }

        AnimatedVisibility(
            visible = showButton,
            enter = expandHorizontally(),
            exit = shrinkHorizontally(),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.shapes.extraLarge,
                        ),
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowCircleUp,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
    BackHandler(true) {}
}