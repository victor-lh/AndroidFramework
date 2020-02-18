package com.victorlh.android.framework.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class UtilPermisos {
	@Retention(RetentionPolicy.SOURCE)
	@IntDef({NUNCA_PEDIDO, CONCEDIDO, DENEGADO, NO_VOLVER_MOSTRAR})
	public @interface PermissionStatus {
	}

	public static final int NUNCA_PEDIDO = -1;
	public static final int CONCEDIDO = 0;
	public static final int DENEGADO = 1;
	public static final int NO_VOLVER_MOSTRAR = 2;

	@PermissionStatus
	public static int getPermissionStatus(Activity activity, String permission) {
		if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
			return CONCEDIDO;
		} else if (!isPermisoPedidos(activity, permission)) {
			return NUNCA_PEDIDO;
		} else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
			return DENEGADO;
		} else {
			return NO_VOLVER_MOSTRAR;
		}
	}

	public static boolean isPermisoConcedido(@NonNull Context context, @NonNull String permiso) {
		int checkPermiso = ContextCompat.checkSelfPermission(context, permiso);
		return checkPermiso == PackageManager.PERMISSION_GRANTED;
	}

	public static void requestPermisos(Activity activity, int resultCode, String... permisos) {
		ActivityCompat.requestPermissions(activity, permisos, resultCode);
		savePermisosPedidos(activity, permisos);
	}

	private static void savePermisosPedidos(Activity activity, String... permisos) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(UtilPermisos.class.getName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sharedPreferences.edit();

		for (String permiso : permisos) {
			edit.putBoolean(UtilPermisos.class.getName() + "#" + permiso, true);
		}

		edit.apply();
	}

	private static boolean isPermisoPedidos(Activity activity, String permiso) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(UtilPermisos.class.getName(), Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(UtilPermisos.class.getName() + "#" + permiso, false);
	}

}
