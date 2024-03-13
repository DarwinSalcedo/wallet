package com.mobile.wallet.data.photo

import android.net.Uri

sealed class PhotoUIEvent{
    class PictureTaken(val uri: Uri) : PhotoUIEvent()
}
