package com.victorlh.android.framework.listas;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Victor
 * 17/12/2018
 */
public abstract class AbstractViewHolder<T extends ItemLista> extends RecyclerView.ViewHolder {

	private AbstractLista<T> lista;
	private boolean isSeleccionado;
	private T item;

	public AbstractViewHolder(View itemView) {
		super(itemView);
	}

	public final void procesar(AbstractLista<T> lista, T item, boolean isSeleccionado) {
		this.lista = lista;
		this.item = item;
		this.isSeleccionado = isSeleccionado;
		onProcesar();
		if (isSeleccionado) {
			onSelect();
		} else {
			onUnselect();
		}
	}

	public T getItem() {
		return item;
	}

	public boolean isSeleccionado() {
		return isSeleccionado;
	}

	protected abstract void onProcesar();

	final void setSeleccion(boolean isSeleccionado) {
		this.isSeleccionado = isSeleccionado;
		if (isSeleccionado) {
			onSelect();
		} else {
			onUnselect();
		}
	}

	public void onSelect() {
	}

	public void onUnselect() {
	}

	protected void notifyItemChange() {
		int itemPosition = lista.getItemPosition(item);
		lista.notifyItemChanged(itemPosition);
	}

	protected AbstractLista<T> getLista() {
		return lista;
	}
}
