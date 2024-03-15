package com.mobile.wallet.domain.home

import com.mobile.wallet.domain.models.Transaction


data class HomeUiState(
    val userName: String? = null,
    val transactions: List<Transaction> = emptyList(),
    val total: Double = transactions.sumOf { it.value },
    val errorMessage: String = "",
    val loading: Boolean = false,
    val navigate: Boolean = false,
)