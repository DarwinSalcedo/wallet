package com.mobile.wallet.data.photo

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.wallet.domain.FirebaseRepository
import com.mobile.wallet.domain.FirebaseRepositoryImpl
import com.mobile.wallet.domain.Result
import kotlinx.coroutines.launch


class PhotoViewModel : ViewModel() {

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

        viewModelScope.launch {

            repository.storeImage(uuid, uri).collect {

                when (it) {
                    is Result.Failure -> {
                        progress.value = false
                    }

                    is Result.Success -> {
                        progress.value = true
                        navigate.value = true
                    }
                }
            }
        }


    }

    fun setUuid(uuid: String?) {
        uIState.value = uIState.value.copy(
            photoId = uuid ?: ""
        )
    }

}
