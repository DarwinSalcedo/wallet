package com.mobile.wallet.data.repository.transaction

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.data.dto.TransactionDto
import com.mobile.wallet.domain.models.Transaction
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TransactionRepositoryImpl : TransactionRepository {

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.firestore
    private val localTransactions = mutableListOf<Transaction>()
    val transactions get() = localTransactions.toList()

    override fun add(transaction: Transaction)  =  callbackFlow{

        localTransactions.add(transaction)
        localTransactions.sortByDescending { it.date }

        auth.uid.let { uuid ->
            if (uuid.isNullOrEmpty()) {
                trySend(Result.Failure(java.lang.Exception("Not found uuid")))
            } else {
                database.collection("transactions").add(transaction.toTransactionDto(uuid))
                    .addOnSuccessListener { documents ->

                        Log.d("TAG", "DocumentSnapshot data: ${documents}")
                        trySend(Result.Success("OK"))
                    }
                    .addOnFailureListener {
                        Log.d("TAG", "Exception= ${it.localizedMessage}")
                        trySend(Result.Failure(it))
                    }
            }
        }
        awaitClose {}
    }

    override fun fetch(): Flow<Result<String>> = callbackFlow {
        Log.e("TAG", "fetchTransactions::: ")
        auth.uid.let { uuid ->
            if (uuid.isNullOrEmpty()) {
                trySend(Result.Failure(java.lang.Exception("Not found uuid")))
            } else {
                Log.e("TAG", "fetchTransactions::: $uuid")

                database.collection("transactions")
                    .whereEqualTo("userId", uuid)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val transactions = mutableListOf<Transaction>()

                            for (document in documents) {
                                val transactionDto = document.toObject(TransactionDto::class.java)
                                transactions.add(transactionDto.toTransaction())
                            }
                            transactions.sortByDescending { it.date }
                            localTransactions.clear()
                            localTransactions.addAll(transactions)

                            trySend(Result.Success(""))

                        } else {
                            trySend(Result.Failure(Exception("Not data")))
                        }
                    }
                    .addOnFailureListener {
                        Log.d("fetchTransactions", "Exception= ${it.localizedMessage}")
                        trySend(Result.Failure(it))
                    }
            }
        }

        awaitClose {}
    }


}