package com.mobile.wallet.data.repository.auth

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.domain.models.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import java.util.UUID

class FirebaseRepositoryImpl : FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.firestore
    private val storage = Firebase.storage.getReference("images")
    override fun getUser(): String? {
        return auth.currentUser?.email
    }

    override fun inSession(): Boolean {
        val result = (!auth.uid.isNullOrEmpty())
        return result
    }

    override fun logout() {
        auth.signOut()
    }

    override suspend fun login(email: String, password: String): Flow<Result<String>> =
        callbackFlow {
            auth
                .signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    trySend(Result.Success(auth.uid.toString()))
                }.addOnFailureListener {
                    trySend(Result.Failure(it))
                }
            awaitClose {}
        }

    override suspend fun createAccount(user: User): Flow<Result<String>> = callbackFlow {

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {

                FirebaseAuth.getInstance().uid.let { uuid ->
                    if (uuid.isNullOrEmpty()) {
                        trySend(Result.Failure(Exception("Not found uuid")))
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

        storage.child("${auth.uid}/${UUID.randomUUID()}.jpg")
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

