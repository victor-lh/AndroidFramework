package com.victorlh.android.framework.core.errores

open class ErrorGeneral : Exception {

	constructor()
	constructor(message: String) : super(message)
	constructor(message: String, cause: Throwable) : super(message, cause)
	constructor(cause: Throwable) : super(cause)

	var avisos: Array<AvisoError> = emptyArray()

}
