package com.mobile.wallet.data.app

import android.app.Application
import com.google.firebase.FirebaseApp


class FakeWalletApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}