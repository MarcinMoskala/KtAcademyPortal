package com.marcinmoskala.kotlinacademy.ui.view.notifications

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.iid.FirebaseInstanceId



class FirebaseIdService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("FirebaseIdService", "Refreshed token: " + refreshedToken!!)
    }
}