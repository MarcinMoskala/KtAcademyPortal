package academy.kot.portal.android

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat.getColor
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun View.hide() {
    visible = false
}

fun View.show() {
    visible = true
}

fun ImageView.loadImage(photoUrl: String) {
    Glide.with(context)
            .load(photoUrl)
            .asBitmap()
            .transform(BorderTransformation(context, 2, getColor(context, R.color.greyFrame)))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}

private class BorderTransformation(context: Context, private val borderSize: Int, private @ColorInt val borderColor: Int) : BitmapTransformation(context) {

    override fun transform(pool: BitmapPool, bmp: Bitmap, outWidth: Int, outHeight: Int): Bitmap =
            bmp.addBorder(borderSize, borderColor)

    override fun getId(): String = this.javaClass.name
}

private fun Bitmap.addBorder(borderSize: Int, @ColorInt color: Int): Bitmap {
    val bmpWithBorder = Bitmap.createBitmap(width + borderSize * 2, height + borderSize * 2, config)
    val canvas = Canvas(bmpWithBorder)
    canvas.drawColor(color)
    canvas.drawBitmap(this, borderSize.toFloat(), borderSize.toFloat(), null)
    return bmpWithBorder
}