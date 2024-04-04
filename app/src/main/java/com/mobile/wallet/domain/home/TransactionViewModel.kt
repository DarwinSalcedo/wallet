package com.mobile.wallet.domain.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.data.repository.transaction.TransactionRepository
import com.mobile.wallet.domain.models.Transaction
import com.mobile.wallet.utils.categories
import com.mobile.wallet.utils.positiveCategories
import com.mobile.wallet.utils.toValidDouble
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(val repository: TransactionRepository) :
    ViewModel() {

    val categoryList = categories

    val error = mutableStateOf("")
    private val _successExecution = mutableStateOf(false)
    val successExecution: State<Boolean> = _successExecution

    fun validate(indexCategory: Int, amount: String, note: String) {
        if (indexCategory == -1) {
            error.value = "Select a category"
            return
        }

        val category = categoryList[indexCategory]

        var amountCalculated = amount.toValidDouble()
        if (!positiveCategories.contains(category.first) && (amountCalculated != 0.0)) {
            amountCalculated *= -1
        }

        val transaction =
            Transaction(category = category.first, value = amountCalculated, note = note)

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