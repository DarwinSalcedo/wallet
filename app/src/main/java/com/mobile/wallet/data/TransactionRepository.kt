package com.mobile.wallet.data

import com.mobile.wallet.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    var localTransactions: MutableList<Transaction>
    fun add(transaction: Transaction)

    fun fetch(): Flow<Result<String>>

}