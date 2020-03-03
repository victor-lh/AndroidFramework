package com.victorlh.framework.entorno.cargadapp

open class ItemsParaleloCargaDApp(itemsCarga: Array<ItemCargaDApp>) : ItemCargaDApp {

	private val items: Array<out ItemCargaDApp> = itemsCarga
	private var controlItems: Int = itemsCarga.count()


	override fun ejecutar(onCompleteListener: () -> Unit) {
		items.forEach {
			it.ejecutar {
				controlItems--
				if (controlItems == 0) {
					onCompleteListener.invoke()
				}
			}
		}
	}

}