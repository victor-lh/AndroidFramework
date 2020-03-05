package com.victorlh.android.framework.core.util

import android.app.Activity
import android.content.pm.PackageManager
import com.victorlh.android.framework.core.util.UtilPermisos.isPermisoConcedido
import com.victorlh.android.framework.core.util.UtilPermisos.requestPermisos
import java.util.*

/**
 * @author Victor
 * 24/03/2018
 */
abstract class AbstractPermisos protected constructor(val activity: Activity, val permisos: Array<String>) {

	fun loadPermisos(resultCode: Int) {
		val permisosNecesarios = permisosSinConceder
		if (permisosNecesarios.isNotEmpty()) {
			requestPermisos(activity, resultCode, *permisosNecesarios)
		}
	}

	private val permisosSinConceder: Array<String>
		get() {
			val permisosNecesarios = ArrayList<String>()
			for (permiso in permisos) {
				if (!isPermisoConcedido(activity, permiso)) {
					permisosNecesarios.add(permiso)
				}
			}
			return permisosNecesarios.toTypedArray()
		}

	val isPermisosConcedidos: Boolean
		get() = permisosSinConceder.isEmpty()

	fun tratarRespuestaPermisos(permissions: Array<String?>, grantResults: IntArray) {
		for (i in permissions.indices) {
			tratrarPermiso(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED)
		}
	}

	protected abstract fun tratrarPermiso(permiso: String?, aceptado: Boolean)

}