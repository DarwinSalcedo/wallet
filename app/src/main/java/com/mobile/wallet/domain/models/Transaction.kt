package com.mobile.wallet.domain.models

import com.mobile.wallet.data.dto.TransactionDto
import java.util.Date
import java.util.UUID

class Transaction(
    val uuid: String = UUID.randomUUID().toString(),
    val category: String = "",
    val note: String = "",
    val value: Double = 0.0,
    val date: Date = Date()
) {
    fun toTransactionDto(userId: String): TransactionDto {
        return TransactionDto(userId, uuid, category, note, value, date)
    }

    override fun toString(): String {
        return "uuid ::" + uuid + "\n" +
                "category ::" + category + "\n" +
                "value ::" + value + "\n" +
                "date ::" + date + "\n"
    }
}
