package com.marcinmoskala.kotlinacademy.ui.view.notifications

import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.ui.common.notificationManager
import com.marcinmoskala.kotlinacademy.ui.view.news.NewsActivityStarter


class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage.notification?.body.orEmpty())
    }
}