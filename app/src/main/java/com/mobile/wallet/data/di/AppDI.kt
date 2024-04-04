package com.mobile.wallet.data.di

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
    fun provideFirebaseRepository(): FirebaseRepository {
        return FirebaseRepositoryImpl()
    }

    @Provides
    fun provideTransactionRepository(): TransactionRepository {
        return TransactionRepositoryImpl()
    }

}