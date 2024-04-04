package com.mobile.wallet.domain.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import com.mobile.wallet.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: TransactionRepository,
    val authRepository: FirebaseRepository
) : ViewModel() {

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


    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            delay(1000)
            _uiState.value = _uiState.value.copy(
                navigate = true
            )
        }
    }


    fun refresh() {
        getData()
    }

    fun resetError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = ""
        )
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
                            loading = false
                        )
                    }

                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "",
                            loading = false,
                            transactions = result.data,
                            total = result.data.sumOf { it.value }
                        )
                    }
                }
            }
        }
    }
}