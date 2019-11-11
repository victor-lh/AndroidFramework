package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import com.victorlh.android.framework.listas.ItemLista;

public class ItemListaShimmerMinimo implements ItemLista {

	private final long number;

	public ItemListaShimmerMinimo(long number) {
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
