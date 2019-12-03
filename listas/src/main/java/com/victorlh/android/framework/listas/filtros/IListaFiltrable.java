package com.victorlh.android.framework.listas.filtros;

import com.victorlh.android.framework.listas.ItemLista;

@Deprecated
public interface IListaFiltrable<T extends ItemLista> {

	void filtrar();

	void addFiltro(IFiltroLista<T> filtro);

	void removeFiltro(IFiltroLista<T> filtro);

	void removeAllFiltros();
}
