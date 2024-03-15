package com.mobile.wallet.domain

import androidx.lifecycle.ViewModel
import com.mobile.wallet.data.FirebaseRepository
import com.mobile.wallet.data.FirebaseRepositoryImpl

class MainViewModel : ViewModel() {

    private val repository: FirebaseRepository = FirebaseRepositoryImpl()

    val inSession: Boolean
        get() {
            return repository.inSession()
        }
}