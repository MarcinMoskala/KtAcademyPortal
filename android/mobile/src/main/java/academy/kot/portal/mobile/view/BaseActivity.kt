package academy.kot.portal.mobile.view

import academy.kot.portal.android.UnsupportedVersionError
import academy.kot.portal.android.toast
import academy.kot.portal.mobile.BuildConfig
import academy.kot.portal.mobile.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.coroutines.experimental.CancellationException
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.presentation.BaseView
import org.kotlinacademy.presentation.Presenter

abstract class BaseActivity : AppCompatActivity(), BaseView {

    protected fun <T : Presenter> presenter(init: () -> T) = lazy(init)
            .also { lazyPresenters += it }

    private var lazyPresenters: List<Lazy<Presenter>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lazyPresenters.forEach { it.value.onCreate() }
    }

    override fun onDestroy() {
        super.onDestroy()
        lazyPresenters.forEach { it.value.onDestroy() }
    }

    override fun showError(error: Throwable) {
        logError(error)
        when (error) {
            is CancellationException -> { /* no-op */ }
            is UnsupportedVersionError -> forceAppUpdate()
            is HttpError -> toast("Http error! Code: ${error.code} Message: ${error.message}")
            else -> toast("Error ${error.message}")
        }
    }

    override fun logError(error: Throwable) {
        if (BuildConfig.DEBUG) Log.e(this::class.simpleName, error.message, error)
    }

    private fun forceAppUpdate() {
        showAlertDialog(R.string.update_available_title, R.string.update_available_description,
                action = {
                    showAppOnGooglePlay()
                    finish()
                }
        )
    }

    fun Context.showAlertDialog(titleId: Int, descriptionId: Int, action: () -> Unit, onCancel: () -> Unit = {}): AlertDialog {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert) else AlertDialog.Builder(this)
        return builder.setTitle(titleId)
                .setMessage(descriptionId)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    action()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(true)
                .setOnCancelListener { onCancel() }
                .show()
    }

    private fun Context.showAppOnGooglePlay() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }
}