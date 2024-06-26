package com.mobile.wallet.domain.photo

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.core.Result
import com.mobile.wallet.data.repository.auth.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoViewModel @Inject constructor(val repository: FirebaseRepository) : ViewModel() {

    private var job: Job? = null

    var uIState = mutableStateOf(PhotoUIState())

    var progress = mutableStateOf(false)

    var navigate = mutableStateOf(false)

    fun onEvent(event: PhotoUIEvent) {
        when (event) {
            is PhotoUIEvent.PictureTaken -> {
                storePhotoInFirebase(uIState.value.photoId, event.uri)
            }
        }
    }


    private fun storePhotoInFirebase(uuid: String, uri: Uri) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(100)
            progress.value = true
            repository.storeImage(uuid, uri).collect {

                when (it) {
                    is Result.Failure -> {
                        progress.value = false
                    }

                    is Result.Success -> {
                        progress.value = false
                        navigate.value = true
                    }
                }
            }
        }


    }

    fun resetPostNavigation() {
        this.navigate.value = false
        progress.value = false
    }

}
