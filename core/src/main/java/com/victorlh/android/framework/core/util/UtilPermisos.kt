package com.victorlh.android.framework.core.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.IntDef
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object UtilPermisos {

	@Retention(AnnotationRetention.SOURCE)
	@IntDef(NUNCA_PEDIDO, CONCEDIDO, DENEGADO, NO_VOLVER_MOSTRAR)
	annotation class PermissionStatus

	const val NUNCA_PEDIDO = -1
	const val CONCEDIDO = 0
	const val DENEGADO = 1
	const val NO_VOLVER_MOSTRAR = 2

	@PermissionStatus
	fun getPermissionStatus(activity: Activity, permission: String): Int {
		return if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
			CONCEDIDO
		} else if (!isPermisoPedidos(activity, permission)) {
			NUNCA_PEDIDO
		} else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
			DENEGADO
		} else {
			NO_VOLVER_MOSTRAR
		}
	}

	fun isPermisoConcedido(context: Context, permiso: String): Boolean {
		val checkPermiso = ContextCompat.checkSelfPermission(context, permiso)
		return checkPermiso == PackageManager.PERMISSION_GRANTED
	}

	fun requestPermisos(activity: Activity, resultCode: Int, vararg permisos: String) {
		ActivityCompat.requestPermissions(activity, permisos, resultCode)
		savePermisosPedidos(activity, *permisos)
	}

	private fun savePermisosPedidos(activity: Activity, vararg permisos: String) {
		val sharedPreferences = activity.getSharedPreferences(UtilPermisos::class.java.name, Context.MODE_PRIVATE)
		val edit = sharedPreferences.edit()
		for (permiso in permisos) {
			edit.putBoolean(UtilPermisos::class.java.name + "#" + permiso, true)
		}
		edit.apply()
	}

	private fun isPermisoPedidos(activity: Activity, permiso: String): Boolean {
		val sharedPreferences = activity.getSharedPreferences(UtilPermisos::class.java.name, Context.MODE_PRIVATE)
		return sharedPreferences.getBoolean(UtilPermisos::class.java.name + "#" + permiso, false)
	}
}