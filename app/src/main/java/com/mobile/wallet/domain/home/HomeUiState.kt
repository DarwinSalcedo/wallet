package com.mobile.wallet.domain.home

import com.mobile.wallet.domain.models.Transaction


data class HomeUiState(
    val userName: String? = null,
    val transactions: List<Transaction> = emptyList(),
    val total: Double = 0.0,
    val errorMessage: String = "",
    val loading: Boolean = false,
    val navigate: Boolean = false,
)