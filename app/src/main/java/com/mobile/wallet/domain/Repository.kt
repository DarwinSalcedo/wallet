package com.mobile.wallet.domain

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobile.wallet.data.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

class FirebaseRepositoryImpl : FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.firestore
    private val storage = Firebase.storage.getReference("images")

    override suspend fun login(email: String, password: String): Result<String> {

        val result = auth
            .signInWithEmailAndPassword(email, password)

        if (result.isSuccessful) {
            return Result.Success(result.result.user?.uid ?: "")
        }
        return Result.Failure(result.exception ?: Exception("Error"))
    }

    override suspend fun createAccount(user: User): Flow<Result<String>> = callbackFlow {

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {

                FirebaseAuth.getInstance().uid.let { uuid ->
                    if (uuid.isNullOrEmpty()) {
                        trySend(Result.Failure(java.lang.Exception("Not found uuid")))
                    } else {
                        database.collection("users").document(uuid).set(user)
                            .addOnSuccessListener {
                                trySend(Result.Success(uuid))
                            }
                            .addOnFailureListener {
                                Log.d("TAG", "Exception= ${it.localizedMessage}")
                                trySend(Result.Failure(it))
                            }
                    }

                }
            }
            .addOnFailureListener {
                Log.d("TAG", "Exception= ${it.localizedMessage}")
                trySend(Result.Failure(it))
            }



        awaitClose {}
    }

    override suspend fun storeImage(uuid: String, uri: Uri): Flow<Result<String>> = callbackFlow {

        storage.child("$uuid/${UUID.randomUUID()}.jpg")
            .putFile(uri)
            .addOnSuccessListener {
                trySend(Result.Success(it.metadata?.path ?: ""))
            }
            .addOnFailureListener {
                trySend(Result.Failure(it))
            }

        awaitClose {}
    }
}

interface FirebaseRepository {
    suspend fun login(email: String, password: String): Result<String>
    suspend fun createAccount(user: User): Flow<Result<String>>
    suspend fun storeImage(uuid: String, uri: Uri): Flow<Result<String>>
}