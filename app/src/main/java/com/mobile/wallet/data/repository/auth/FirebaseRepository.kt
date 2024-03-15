package com.mobile.wallet.data.repository.auth

import android.net.Uri
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.domain.models.User
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun getUser(): String?
    fun inSession(): Boolean
    fun logout()
    suspend fun login(email: String, password: String): Flow<Result<String>>
    suspend fun createAccount(user: User): Flow<Result<String>>
    suspend fun storeImage(uuid: String, uri: Uri): Flow<Result<String>>
}