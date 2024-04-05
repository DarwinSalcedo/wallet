package com.mobile.wallet.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mobile.wallet.data.core.DefaultDispatcherProvider
import com.mobile.wallet.data.core.DispatcherProvider
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import com.mobile.wallet.data.repository.auth.FirebaseRepositoryImpl
import com.mobile.wallet.data.repository.transaction.TransactionRepository
import com.mobile.wallet.data.repository.transaction.TransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuthProvider(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideDatabaseProvider(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun provideStorageProvider(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    fun provideFirebaseRepository(
        authProvider: FirebaseAuth,
        databaseProvider: FirebaseFirestore,
        storageProvider: FirebaseStorage,
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(authProvider, databaseProvider, storageProvider)
    }

    @Provides
    fun provideTransactionRepository(): TransactionRepository {
        return TransactionRepositoryImpl()
    }

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

}