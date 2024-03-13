package com.mobile.wallet.data.photo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mobile.wallet.data.User
import com.mobile.wallet.data.rules.Validator


class PhotoViewModel : ViewModel() {

    private val TAG = PhotoViewModel::class.simpleName

    var uIState = mutableStateOf(PhotoUIState())

    var allValidationsPassed = mutableStateOf(false)

    var inProgress = mutableStateOf(false)

    var navigate = mutableStateOf(false)

    init {
        //
        uIState.value = uIState.value.copy(
            photoId = "1234"
        )
    }

    fun onEvent(event: PhotoUIEvent) {
        when (event) {
            is PhotoUIEvent.PictureTaken -> {
                navigate.value = true
            }
        }
        //validateDataWithRules()
    }


    private fun validateDataWithRules() {


        val result = Validator.validatePhotoId(
            path = uIState.value.photoId
        )

        uIState.value = uIState.value.copy(
            photoIdError = result.status
        )

        allValidationsPassed.value = result.status

    }


    private fun storePhotoInFirebase(user: User) {

        inProgress.value = true
        navigate.value = true


    }


}
