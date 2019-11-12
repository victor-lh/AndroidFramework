package com.victorlh.android.framework.listas.filtros;

import com.victorlh.android.framework.listas.ItemLista;

public interface IFiltroLista<T extends ItemLista> {

	boolean isDentroFiltro(T elemento);
}
