package com.victorlh.android.framework.core.flujo;

import java.util.Stack;

public class StackFlujo {

	private Stack<IElementoStackFlujo> stack;

	public StackFlujo() {
		this.stack = new Stack<>();
	}

	public void addElemento(IElementoStackFlujo elementoStackFlujo) {
		stack.push(elementoStackFlujo);
	}

	public IElementoStackFlujo getElementoActual() {
		return stack.isEmpty() ? null : stack.peek();
	}

	public void finishActual() {
		if (!stack.empty()) {
			stack.pop();
		}
	}

	public void clear() {
		stack.clear();
	}

	public int size() {
		return stack.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean isFirstElemento() {
		return size() == 1;
	}
}
