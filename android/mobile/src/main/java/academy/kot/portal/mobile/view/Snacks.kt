package academy.kot.portal.mobile.view

import android.app.Activity
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.getColor
import android.view.View

fun Activity.okSnack(@StringRes textId: Int) {
    okSnack(getString(textId))
}

fun Activity.okSnack(text: String) {
    val parentLayout: View = findViewById(android.R.id.content)
    Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
            .color(android.R.color.holo_green_light)
            .show()
}

private fun Snackbar.color(@ColorRes colorId: Int) = apply {
    view.setBackgroundColor(getColor(context, colorId))
}