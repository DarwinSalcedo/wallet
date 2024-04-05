package com.mobile.wallet.data.repository.auth

import android.net.Uri
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFirebaseRepository(val loginResponse: Boolean) : FirebaseRepository {

    override fun getUser(): String? {
        return "test"
    }

    override fun inSession(): Boolean {
        return true
    }

    override fun logout() {

    }

    override suspend fun login(email: String, password: String): Flow<Result<String>> {
        return if (loginResponse) flowOf(Result.Success("")) else flowOf(Result.Failure(Exception("Test")))
    }

    override suspend fun createAccount(user: User): Flow<Result<String>> {
        return flowOf(Result.Success(""))
    }

    override suspend fun storeImage(uuid: String, uri: Uri): Flow<Result<String>> {
        return flowOf(Result.Success(""))
    }
}