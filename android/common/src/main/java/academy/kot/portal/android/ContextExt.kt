package academy.kot.portal.android

import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.toast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

fun Context.toast(@StringRes textId: Int) {
    toast(getString(textId))
}

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.connectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

val Context.isConnected: Boolean?
    get() = connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting

val Context.activityManager
    get() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

val Context.isAirplaneModeOn: Boolean
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    get() = try {
        Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } catch (error: Throwable) {
        false
    }

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