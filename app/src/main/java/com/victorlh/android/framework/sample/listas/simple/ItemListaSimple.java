package com.victorlh.android.framework.sample.listas.simple;

import com.victorlh.android.framework.listas.ItemLista;

public class ItemListaSimple implements ItemLista {

	private final long number;

	public ItemListaSimple(long number) {
		this.number = number;
	}

	public long getNumber() {
		return number;
	}

	@Override
	public long getId() {
		return getNumber();
	}
}
