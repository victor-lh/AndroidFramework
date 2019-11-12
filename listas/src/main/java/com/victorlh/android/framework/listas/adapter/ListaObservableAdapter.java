package com.victorlh.android.framework.listas.adapter;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.listas.ItemLista;

import java.util.ArrayList;
import java.util.List;

class ListaObservableAdapter<T extends ItemLista> extends ListaObservable<T> {

	private List<ListaObservable<T>> lista;

	ListaObservableAdapter() {
		this.lista = new ArrayList<>();
	}

	void registrarObservable(ListaObservable<T> observable) {
		lista.add(observable);
	}

	void removerObservable(ListaObservable<T> observable) {
		lista.remove(observable);
	}

	protected void setItem(T item) {
		for (int i = lista.size() - 1; i >= 0; i--) {
			ListaObservable<T> observable = lista.get(i);
			observable.setItem(item);
		}
	}

	protected void deleteItem(T item) {
		for (int i = lista.size() - 1; i >= 0; i--) {
			ListaObservable<T> observable = lista.get(i);
			observable.deleteItem(item);
		}
	}

	protected void recargarLista() {
		for (int i = lista.size() - 1; i >= 0; i--) {
			ListaObservable<T> observable = lista.get(i);
			observable.recargarLista();
		}
	}

	protected void onClickElemento(AbstractViewHolder<T> viewHolder) {
		for (int i = lista.size() - 1; i >= 0; i--) {
			ListaObservable<T> observable = lista.get(i);
			observable.onClickElemento(viewHolder);
		}
	}

	protected void onLongClickElemento(AbstractViewHolder<T> viewHolder) {
		for (int i = lista.size() - 1; i >= 0; i--) {
			ListaObservable<T> observable = lista.get(i);
			observable.onLongClickElemento(viewHolder);
		}
	}

	protected void onBindViewHolder(@NonNull final AbstractViewHolder<T> holder, int position) {
		for (int i = lista.size() - 1; i >= 0; i--) {
			ListaObservable<T> observable = lista.get(i);
			observable.onBindViewHolder(holder, position);
		}
	}
}
