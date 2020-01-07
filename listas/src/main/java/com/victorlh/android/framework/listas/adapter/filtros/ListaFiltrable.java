package com.victorlh.android.framework.listas.adapter.filtros;

import com.victorlh.android.framework.listas.ItemLista;

public interface ListaFiltrable<T extends ItemLista> {

	void filtrar();

	void addFiltro(FiltroLista<T> filtro);

	void removeFiltro(FiltroLista<T> filtro);

	void removeAllFiltros();

	void invalidate();
}
