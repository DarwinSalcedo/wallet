package com.mobile.wallet.domain.photo

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.data.FirebaseRepository
import com.mobile.wallet.data.FirebaseRepositoryImpl
import com.mobile.wallet.data.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PhotoViewModel : ViewModel() {

    private var job: Job? = null
    private val TAG = PhotoViewModel::class.simpleName

    var uIState = mutableStateOf(PhotoUIState())

    var progress = mutableStateOf(false)

    var navigate = mutableStateOf(false)

    private var repository: FirebaseRepository = FirebaseRepositoryImpl()


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

    fun setUuid(uuid: String?) {
        println("setting uuid:::" + uuid)
        uIState.value = uIState.value.copy(
            photoId = uuid ?: ""
        )
    }

    fun resetPostNavigation() {
        this.navigate.value = false
        progress.value = false
    }

}
