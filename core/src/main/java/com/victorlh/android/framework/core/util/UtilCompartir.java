package com.victorlh.android.framework.core.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.victorlh.tools.ToolsValidacion;

public class UtilCompartir {

	public static void compartirAudio(@NonNull Context context, @NonNull Uri uri) {
		compartirAudio(context, uri, null);
	}

	public static void compartirAudio(@NonNull Context context, @NonNull Uri uri, @Nullable String txtCompartir) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("audio/*");
		share.putExtra(Intent.EXTRA_STREAM, uri);
		String compartirText = ToolsValidacion.isCadenaVacia(txtCompartir) ? "Shared to" : txtCompartir;
		Intent chooser = Intent.createChooser(share, compartirText);
		context.startActivity(chooser);
	}

	public static void compartirVideo(@NonNull Context context, @NonNull Uri uri) {
		compartirVideo(context, uri, null);
	}

	public static void compartirVideo(@NonNull Context context, @NonNull Uri uri, @Nullable String txtCompartir) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("video/*");
		share.putExtra(Intent.EXTRA_STREAM, uri);
		String compartirText = ToolsValidacion.isCadenaVacia(txtCompartir) ? "Shared to" : txtCompartir;
		Intent chooser = Intent.createChooser(share, compartirText);
		context.startActivity(chooser);
	}

	public static void compartirVideoInstagram(@NonNull Context context, @NonNull Uri uri, @Nullable String txtCompartir) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("video/*");
		share.putExtra(Intent.EXTRA_STREAM, uri);
		share.setPackage("com.instagram.android");
		String compartirText = ToolsValidacion.isCadenaVacia(txtCompartir) ? "Shared to" : txtCompartir;
		Intent chooser = Intent.createChooser(share, compartirText);
		context.startActivity(chooser);
	}
}
