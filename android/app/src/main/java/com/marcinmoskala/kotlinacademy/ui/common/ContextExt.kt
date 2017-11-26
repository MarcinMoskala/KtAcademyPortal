package com.marcinmoskala.kotlinacademy.ui.common

import android.app.NotificationManager
import android.content.Context

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager