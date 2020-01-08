package com.victorlh.android.framework.core.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Victor Latorre
 */
public class Util {

	@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}

		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

	public static boolean isMemoriaMontada() {
		String estado = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(estado);
	}

	public static void showToast(Context context, int textId) {
		showToastShort(context, textId);
	}

	public static void showToastShort(Context context, int textId) {
		showToast(context, textId, Toast.LENGTH_SHORT);
	}

	public static void showToastLong(Context context, int textId) {
		showToast(context, textId, Toast.LENGTH_LONG);
	}

	public static void showToast(Context context, int textId, int tiempo) {
		Toast toast = Toast.makeText(context, textId, tiempo);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showToast(Context context, String text) {
		showToastShort(context, text);
	}

	public static void showToastShort(Context context, String text) {
		showToast(context, text, Toast.LENGTH_SHORT);
	}

	public static void showToastLong(Context context, String text) {
		showToast(context, text, Toast.LENGTH_LONG);
	}

	public static void showToast(Context context, String text, int tiempo) {
		Toast toast = Toast.makeText(context, text, tiempo);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static boolean copyRAWtoAppDirectory(int id, File directory, String fileName, Context context) {
		byte[] buff = new byte[1024];
		int read;
		try (InputStream in = openRAWFile(id, context)) {
			File path = new File(directory, fileName);
			try (FileOutputStream out = new FileOutputStream(path)) {
				while ((read = in.read(buff)) > 0) {
					out.write(buff, 0, read);
				}
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static InputStream openRAWFile(int id, Context context) {
		return context.getResources().openRawResource(id);
	}

	public static boolean isVersionMayor44() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}

	public static String getStringResource(Context context, int id) {
		if (id == 0) {
			return "";
		}
		return context.getResources().getString(id);
	}

	public static String getStringResourceVars(Context context, int id, String... vars) {
		String txt = getStringResource(context, id);
		for (int i = 0; i < vars.length; i++) {
			String aux = "$" + (i + 1);
			txt = txt.replace(aux, vars[i]);
		}
		return txt;
	}

	public static String getPluralResource(Context context, int stringId, int cantidad, Object... vars){
		Resources res = context.getResources();
		return res.getQuantityString(stringId, cantidad, vars);
	}

	public static byte[] toByteArray(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * This method converts dp unit to equivalent pixels, depending on device density.
	 *
	 * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on device density
	 */
	public static float convertDpToPixel(float dp, Context context) {
		return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}

	/**
	 * This method converts device specific pixels to density independent pixels.
	 *
	 * @param px      A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}

	/**
	 * El primero parámetro es la imagen que va a cambiar de color. </br>
	 * La saturación = 0 -> Blanco y negro.
	 * La saturación = 1 -> A color.
	 *
	 * @param imageView
	 * @param saturacion
	 */
	public static void setColorImagen(ImageView imageView, int saturacion) {
		ColorMatrix matrix = new ColorMatrix();
		matrix.setSaturation(saturacion);
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		imageView.setColorFilter(filter);
	}

	public static Bitmap getFrameVideo(Context context, Uri uri, int timeMs) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(context, uri);
		return retriever.getFrameAtTime(timeMs);
	}
}
