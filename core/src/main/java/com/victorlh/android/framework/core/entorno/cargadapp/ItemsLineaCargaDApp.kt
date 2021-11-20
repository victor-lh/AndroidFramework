package com.victorlh.android.framework.core.entorno.cargadapp

import java.util.*

open class ItemsLineaCargaDApp(itemsCarga: Array<ItemCargaDApp>) : ItemCargaDApp {

	private var controlItems: LinkedList<ItemCargaDApp> = LinkedList(itemsCarga.toList())

	override fun ejecutar(onCompleteListener: () -> Unit) {
		val poll = controlItems.poll()
		poll?.ejecutar { ejecutar(onCompleteListener) } ?: onCompleteListener.invoke()
	}


}