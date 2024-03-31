package com.mobile.wallet.domain.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.data.repository.transaction.TransactionRepositoryImpl
import com.mobile.wallet.domain.models.Transaction
import com.mobile.wallet.utils.categories
import com.mobile.wallet.utils.positiveCategories
import com.mobile.wallet.utils.toValidDouble
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel() {

    val categoryList = categories

    private val repository: TransactionRepositoryImpl = TransactionRepositoryImpl()
    val error = mutableStateOf("")
    private val _successExecution = mutableStateOf(false)
    val successExecution: State<Boolean> = _successExecution

    fun validate(indexCategory: Int, value: String) {
        if (indexCategory == -1) {
            error.value = "Select a category"
            return
        }

        val category = categoryList[indexCategory]

        var amount = value.toValidDouble()
        if (!positiveCategories.contains(category.first)) {
            amount *= -1
        }

        val transaction = Transaction(category = category.first, value = amount)

        viewModelScope.launch {
            repository.add(transaction).collect {

                when (it) {
                    is Result.Failure -> {
                        error.value = it.error.localizedMessage.toString()
                    }

                    is Result.Success -> {
                        _successExecution.value = true
                    }
                }
            }
        }
    }

    fun resetError() {
        error.value = ""
    }

    fun resetSuccessExecution() {
        _successExecution.value = false
    }


}