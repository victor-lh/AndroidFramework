package com.victorlh.android.framework.core.util;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.core.util.UtilPermisos;

import java.util.ArrayList;

/**
 * @author Victor
 * 24/03/2018
 */
public abstract class AbstractPermisos {

	private Activity activity;
	private String[] permisos;

	protected AbstractPermisos(Activity activity, String... permisos) {
		this.activity = activity;
		this.permisos = permisos;
	}

	public void loadPermisos(int resultCode) {
		String[] permisosNecesarios = getPermisosSinConceder();
		if (permisosNecesarios.length > 0) {
			UtilPermisos.requestPermisos(activity, resultCode, permisosNecesarios);
		}
	}

	private String[] getPermisosSinConceder() {
		ArrayList<String> permisosNecesarios = new ArrayList<>();
		for (String permiso : permisos) {
			if (!UtilPermisos.isPermisoConcedido(activity, permiso)) {
				permisosNecesarios.add(permiso);
			}
		}
		return permisosNecesarios.toArray(new String[0]);
	}

	public void tratarRespuestaPermisos(@NonNull String[] permissions, @NonNull int[] grantResults) {
		for (int i = 0; i < permissions.length; i++) {
			tratrarPermiso(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
		}
	}

	protected abstract void tratrarPermiso(String permiso, boolean aceptado);

	public String[] getPermisos() {
		return permisos;
	}

	public Activity getActivity() {
		return activity;
	}
}
