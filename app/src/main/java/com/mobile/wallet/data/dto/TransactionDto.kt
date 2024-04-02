package com.mobile.wallet.data.dto

import com.mobile.wallet.domain.models.Transaction
import java.util.Date
import java.util.UUID

class TransactionDto(
    val userId: String = UUID.randomUUID().toString(),
    val uuid: String = UUID.randomUUID().toString(),
    val category: String = "",
    val note: String = "",
    val value: Double = 0.0,
    val date: Date = Date()

){
    fun toTransaction() : Transaction {
        return Transaction( uuid, category, note,value, date)
    }
}