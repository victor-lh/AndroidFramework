package com.victorlh.android.framework.listas.adapter;

import com.victorlh.android.framework.listas.ItemLista;

public abstract class ListaObservable<T extends ItemLista> {

	protected void setItem(T item) {
	}

	protected void deleteItem(T item) {
	}

	protected void recargarLista() {
	}

	protected void onClickElemento(AbstractViewHolder<T> viewHolder) {
	}

	protected void onLongClickElemento(AbstractViewHolder<T> viewHolder) {
	}
}
