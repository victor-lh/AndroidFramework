package com.victorlh.android.framework.listas.adapter;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.listas.ItemLista;

public abstract class ListaObservable<T extends ItemLista> {

	protected void setItem(T item) {
	}

	protected void deleteItem(T item) {
	}

	protected void recargarLista() {
	}

	protected boolean onClickElemento(AbstractViewHolder<T> viewHolder) {
		return false;
	}

	protected boolean onLongClickElemento(AbstractViewHolder<T> viewHolder) {
		return false;
	}

	protected void onBindViewHolder(@NonNull final AbstractViewHolder<T> holder, int position) {
	}
}
