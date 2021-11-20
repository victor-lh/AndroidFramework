package com.victorlh.android.framework.core.flujo

import java.util.*

open class StackFlujo {

	private val stack: Stack<IElementoStackFlujo> = Stack()

	fun addElemento(elementoStackFlujo: IElementoStackFlujo) {
		stack.push(elementoStackFlujo)
	}

	open val elementoActual: IElementoStackFlujo?
		get() = if (stack.isEmpty()) null else stack.peek()

	fun finishActual() {
		if (!stack.empty()) {
			stack.pop()
		}
	}

	fun clear() {
		stack.clear()
	}

	val size: Int
		get() = stack.size

	val isEmpty: Boolean
		get() = size == 0

	val isFirstElemento: Boolean
		get() = size == 1

}