package org.kotlinacademy.common

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.toast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun Context.openUrl(url: String?) {
    url.nullIfBlank() ?: return
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (browserIntent.resolveActivity(packageManager) != null) {
        startActivity(browserIntent)
    }
}

fun Context.startShareIntent(subject: String, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(intent, "Share via"))
}

fun Context.canShare() = Intent(Intent.ACTION_SEND).resolveActivity(packageManager) != null