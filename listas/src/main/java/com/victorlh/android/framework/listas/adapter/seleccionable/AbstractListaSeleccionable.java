package com.victorlh.android.framework.listas.adapter.seleccionable;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.listas.ItemLista;
import com.victorlh.android.framework.listas.adapter.AbstractLista;

import java.util.List;

/**
 * Created by victor on 07/07/2017.
 */
public abstract class AbstractListaSeleccionable<T extends ItemLista> extends AbstractLista<T> implements ListaSeleccionable<T> {

	private ListaSeleccionableAdapter<T> adapter;

	protected AbstractListaSeleccionable(Class<T> clazz) {
		this(clazz, ETipoSeleccion.NONE);
	}

	protected AbstractListaSeleccionable(Class<T> clazz, ETipoSeleccion tipoSeleccion) {
		super(clazz);
		this.adapter = new ListaSeleccionableAdapter<>(tipoSeleccion);
		this.adapter.attachLista(this);
	}

	@Override
	public void setTipoSeleccion(@NonNull ETipoSeleccion tipoSeleccion) {
		adapter.setTipoSeleccion(tipoSeleccion);
	}

	@Override
	public ETipoSeleccion getTipoSeleccion() {
		return adapter.getTipoSeleccion();
	}

	@Override
	public void setSeleccion(@NonNull T[] items) {
		adapter.setSeleccion(items);
	}

	@Override
	public void setSeleccion(@NonNull List<T> items) {
		adapter.setSeleccion(items);
	}

	@Override
	public void addSeleccion(@NonNull T item) {
		adapter.addSeleccion(item);
	}

	@Override
	public void removeSeleccion(@NonNull T item) {
		adapter.removeSeleccion(item);
	}

	@Override
	public void removeAllSeleccion() {
		adapter.removeAllSeleccion();
	}

	@Override
	public boolean haySeleccionados() {
		return adapter.haySeleccionados();
	}

	@Override
	public boolean isSeleccionado(@NonNull T item) {
		return adapter.isSeleccionado(item);
	}

	@Override
	public T[] getItemsSeleccionados() {
		return adapter.getItemsSeleccionados();
	}

	@Override
	public void addOnStartSeleccionListener(@NonNull OnStartSeleccionListener onStartSeleccionListener) {
		adapter.addOnStartSeleccionListener(onStartSeleccionListener);
	}

	@Override
	public void addOnStopSeleccionListener(@NonNull OnStopSeleccionListener onStopSeleccionListener) {
		adapter.addOnStopSeleccionListener(onStopSeleccionListener);
	}

	@Override
	public void addOnSeleccionChangeListener(@NonNull OnSeleccionChangeListener onSeleccionChangeListener) {
		adapter.addOnSeleccionChangeListener(onSeleccionChangeListener);
	}
}