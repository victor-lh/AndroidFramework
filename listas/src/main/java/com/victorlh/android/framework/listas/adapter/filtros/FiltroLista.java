package com.victorlh.android.framework.listas.adapter.filtros;

import com.victorlh.android.framework.listas.ItemLista;

public interface FiltroLista<T extends ItemLista> {

	boolean isDentroFiltro(T elemento);
}
