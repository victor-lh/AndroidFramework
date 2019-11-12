package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import com.victorlh.android.framework.listas.ItemLista;
import com.victorlh.tools.ordenacion.ElementoOrdenado;
import com.victorlh.tools.ordenacion.KeyOrdenacion;
import com.victorlh.tools.ordenacion.datoordenacion.DatoOrdenacion;
import com.victorlh.tools.ordenacion.datoordenacion.DatoOrdenacionString;

public class ItemListaShimmerMinimo implements ItemLista, ElementoOrdenado {

	public enum Ordenacion implements KeyOrdenacion {
		A
	}

	private final long number;
	private final String text;

	public ItemListaShimmerMinimo(long number, String text) {
		this.number = number;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public long getId() {
		return number;
	}

	@Override
	public DatoOrdenacion getDatoOrdenacion(KeyOrdenacion keyOrdenacion) {
		return new DatoOrdenacionString(getText());
	}
}
