package com.mobile.wallet.domain.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.FirebaseRepository
import com.mobile.wallet.data.FirebaseRepositoryImpl
import com.mobile.wallet.data.Result
import com.mobile.wallet.data.TransactionRepositoryImpl
import com.mobile.wallet.domain.models.Transaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository: TransactionRepositoryImpl = TransactionRepositoryImpl()
    private val authRepository: FirebaseRepository = FirebaseRepositoryImpl()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                userName = authRepository.getUser(),
            )
            getData()
        }
    }

    fun addTransaction(transaction: Transaction) {
        repository.add(transaction)
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.value = _uiState.value.copy(
            errorMessage = "",
            loading = false,
            transactions = repository.transactions,
            total = repository.transactions.sumOf { it.value }
        )
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            delay(1000)
            _uiState.value = _uiState.value.copy(
                navigate = true)
        }
    }

    private fun getData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                loading = true
            )

            repository.fetch().collect {

                when (val result = it) {
                    is Result.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.error.localizedMessage,
                            loading = false,
                            transactions = repository.transactions,
                            total = repository.transactions.sumOf { it.value }
                        )
                    }

                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "",
                            loading = false,
                            transactions = repository.transactions,
                            total = repository.transactions.sumOf { it.value }
                        )
                    }
                }
            }
        }
    }
}