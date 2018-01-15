package org.kotlinacademy.common

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun Context.openUrl(url: String?) {
    url.nullIfBlank() ?: return
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    browserIntent.resolveActivity(packageManager).let { startActivity(browserIntent) }
}

fun Context.startShareIntent(subject: String, text: String) {
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }.also {
        startActivity(Intent.createChooser(it, "Share via"))
    }
}

fun Context.canShare() = Intent(Intent.ACTION_SEND).resolveActivity(packageManager) != null