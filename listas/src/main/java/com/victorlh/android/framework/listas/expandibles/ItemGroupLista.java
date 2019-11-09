package com.victorlh.android.framework.listas.expandibles;

import com.victorlh.android.framework.listas.ItemLista;

/**
 * @author Victor
 * 09/11/2018
 */
public interface ItemGroupLista<T extends ItemChildLista> extends ItemLista {

	T[] getChilds();
}
