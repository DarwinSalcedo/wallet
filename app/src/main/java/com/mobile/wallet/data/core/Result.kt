package com.mobile.wallet.data.core

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: Exception) : Result<Nothing>()
}