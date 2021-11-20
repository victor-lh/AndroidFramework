package com.victorlh.android.framework.core.util

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.media.MediaMetadataRetriever
import android.net.ConnectivityManager
import android.net.Uri
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.annotation.RequiresPermission
import java.io.ByteArrayOutputStream

object Util

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//TODO - Cambiar por una clase que controle los callback de la red {@link ConnectivityManager.NetworkCallback}
fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: return false
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun Bitmap.toByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Util.convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px      A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun Util.convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * El primero parámetro es la imagen que va a cambiar de color.
 * La saturación = 0 -> Blanco y negro.
 * La saturación = 1 -> A color.
 *
 * @param imageView
 * @param saturacion
 */
fun ImageView.setSaturacionColorImagen(saturacion: Int) {
    val matrix = ColorMatrix()
    matrix.setSaturation(saturacion.toFloat())
    val filter = ColorMatrixColorFilter(matrix)
    colorFilter = filter
}

fun Util.getFrameVideo(context: Context, uri: Uri, timeMs: Int): Bitmap? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(context, uri)
    return retriever.getFrameAtTime(timeMs.toLong())
}