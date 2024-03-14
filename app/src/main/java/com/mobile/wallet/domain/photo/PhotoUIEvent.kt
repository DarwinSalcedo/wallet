package com.mobile.wallet.domain.photo

import android.net.Uri

sealed class PhotoUIEvent{
    class PictureTaken(val uri: Uri) : PhotoUIEvent()
}
