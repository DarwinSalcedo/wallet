package com.mobile.wallet.domain.main

import androidx.lifecycle.ViewModel
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import com.mobile.wallet.data.repository.auth.FirebaseRepositoryImpl

class MainViewModel : ViewModel() {

    private val repository: FirebaseRepository = FirebaseRepositoryImpl()

    val inSession: Boolean
        get() {
            return repository.inSession()
        }
}