package com.mobile.wallet.domain.main

import androidx.lifecycle.ViewModel
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import com.mobile.wallet.data.repository.auth.FirebaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: FirebaseRepository) : ViewModel() {
    val inSession: Boolean
        get() {
            return repository.inSession()
        }
}