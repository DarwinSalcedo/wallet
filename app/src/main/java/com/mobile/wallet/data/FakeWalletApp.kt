package com.mobile.wallet.data

import android.app.Application
import com.google.firebase.FirebaseApp


class FakeWalletApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}