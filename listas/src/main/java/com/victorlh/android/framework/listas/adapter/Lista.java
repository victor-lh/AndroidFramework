package com.victorlh.android.framework.listas.adapter;

import com.victorlh.android.framework.listas.ItemLista;

import java.util.List;

public interface Lista<T extends ItemLista> {

	long getItemId(int position);

	List<T> getLista();

	T getItem(int position);

	int getItemCount();

	int getItemPosition(T item);

	int getItemPosition(T item, boolean byId);

	T getItemById(long id);

	void setLista(T[] lista);

	void setLista(List<T> lista);

	void addItem(T item);

	void addItem(T item, int position);

	void setItem(T item);

	void deleteItem(T item);

	void recargarLista();

	void setOnClickElementoListener(OnClickElementoListener<T> onClickElementoListener);

	void setOnLongClickElementoListener(OnLongClickElementoListener<T> onLongClickElementoListener);

	void setOnDataListChangeListener(OnDataListChangeListener onDataListChangeListener);

	interface OnClickElementoListener<T extends ItemLista> {
		void onClickElemento(AbstractViewHolder<T> viewHolder);
	}

	interface OnLongClickElementoListener<T extends ItemLista> {
		boolean onLongClickElemento(AbstractViewHolder<T> viewHolder);
	}

	interface OnDataListChangeListener {
		void onSetData();

		void onVoidData();

		void onChangeData();
	}
}
