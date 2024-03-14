package com.mobile.wallet.domain.models

data class User(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val photoId: String = "",
)