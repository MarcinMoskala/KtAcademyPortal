package org.kotlinacademy.ui.view.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import org.kotlinacademy.R
import org.kotlinacademy.ui.common.notificationManager
import org.kotlinacademy.ui.view.news.NewsActivityStarter

fun Context.showNotification(message: String) {
    val intent = NewsActivityStarter.getIntent(this).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

    val notification = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.icon_notification)
            .setContentTitle(getString(R.string.portal_name))
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .build()

    notificationManager.notify(1234, notification)
}