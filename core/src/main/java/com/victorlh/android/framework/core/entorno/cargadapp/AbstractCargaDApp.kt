package com.victorlh.android.framework.core.entorno.cargadapp

import com.victorlh.android.framework.logger.Logger


/**
 * @author Victor
 * 01/09/2018
 */
abstract class AbstractCargaDApp {

	companion object {
		const val TAG = "CargaAPP"
	}

	private val items = ArrayList<ItemCargaDApp>()
	private var start: Long = 0

	fun cargar() {
		Logger.d(TAG, "Start carga de app")

		start = System.currentTimeMillis()
		loadTareas()

		val itemsParaleloCargaDApp = ItemsParaleloCargaDApp(items.toTypedArray())
		itemsParaleloCargaDApp.ejecutar {
			terminarEjecucion()
		}
	}

	protected abstract fun loadTareas()
	protected abstract fun onFinish()

	protected fun registrarItem(item: ItemCargaDApp) {
		this.items.add(item)
	}

	private fun terminarEjecucion() {
		Logger.d(TAG, "Fin carga de app " + (System.currentTimeMillis() - start) + " ms")
		onFinish()
	}
}