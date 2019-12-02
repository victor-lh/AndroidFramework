package com.victorlh.android.framework.listas.adapter.seleccionable;

import android.util.Log;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.listas.ItemLista;
import com.victorlh.android.framework.listas.adapter.AbstractLista;
import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.adapter.ListaObservable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaSeleccionableAdapter<T extends ItemLista> implements ListaSeleccionable<T> {

	private AbstractLista<T> listaAdapter;
	private ListaSeleccionableObservable observable;

	private ETipoSeleccion tipoSeleccion;
	private List<T> seleccion;

	private List<OnStartSeleccionListener> onStartSeleccionListener;
	private List<OnStopSeleccionListener> onStopSeleccionListener;
	private List<OnSeleccionChangeListener> onSeleccionChangeListeners;
	private List<OnItemSeleccionadoListener<T>> onItemSeleccionadoListeners;
	private List<OnItemDeseleccionadoListener<T>> onItemDeseleccionadoListeners;

	public ListaSeleccionableAdapter(ETipoSeleccion tipoSeleccion) {
		this.tipoSeleccion = tipoSeleccion;

		this.seleccion = new ArrayList<>();
		this.onStartSeleccionListener = new ArrayList<>();
		this.onStopSeleccionListener = new ArrayList<>();
		this.onSeleccionChangeListeners = new ArrayList<>();
		this.onItemSeleccionadoListeners = new ArrayList<>();
		this.onItemDeseleccionadoListeners = new ArrayList<>();

		this.observable = new ListaSeleccionableObservable();
	}

	@Override
	public void setTipoSeleccion(@NonNull ETipoSeleccion tipoSeleccion) {
		this.tipoSeleccion = tipoSeleccion;
		if (tipoSeleccion == ETipoSeleccion.NONE && haySeleccionados()) {
			removeAllSeleccion();
		}
	}

	@Override
	public ETipoSeleccion getTipoSeleccion() {
		return tipoSeleccion;
	}

	@Override
	public void setSeleccion(@NonNull T[] items) {
		setSeleccion(Arrays.asList(items));
	}

	@Override
	public void setSeleccion(@NonNull List<T> items) {
		checkAttached();
		removeAllSeleccion();
		for (T item : items) {
			long id = item.getId();
			T elementoLista = listaAdapter.getItemById(id);
			if (elementoLista != null) {
				addSeleccion(elementoLista);
			}
		}
	}

	@Override
	public void addSeleccion(@NonNull T item) {
		checkAttached();
		boolean haySeleccionados = haySeleccionados();
		seleccion.add(item);
		AbstractViewHolder<T> viewHolder = listaAdapter.getViewHolder(item);
		if (viewHolder != null) {
			viewHolder.setSeleccion(true);
		}
		if (!haySeleccionados) {
			startSeleccion();
		}
		onItemSeleccionado(item);
		onSeleccionChange();
	}

	@Override
	public void removeSeleccion(@NonNull T item) {
		Log.d("AAAAAA", "Remove seleccion");
		checkAttached();
		seleccion.remove(item);
		AbstractViewHolder<T> viewHolder = listaAdapter.getViewHolder(item);
		if (viewHolder != null) {
			viewHolder.setSeleccion(false);
		}
		onItemDeseleccionado(item);
		onSeleccionChange();
		if (!haySeleccionados()) {
			stopSeleccion();
		}
	}

	@Override
	public void removeAllSeleccion() {
		checkAttached();
		seleccion.clear();
		List<T> lista = listaAdapter.getLista();
		for (T item : lista) {
			AbstractViewHolder<T> viewHolder = listaAdapter.getViewHolder(item);
			if (viewHolder != null) {
				viewHolder.setSeleccion(false);
			}
			onItemDeseleccionado(item);
		}
		onSeleccionChange();
		stopSeleccion();
	}

	private void removeAllSeleccionSinEventos() {
		checkAttached();
		seleccion.clear();
		List<T> lista = listaAdapter.getLista();
		for (T item : lista) {
			AbstractViewHolder<T> viewHolder = listaAdapter.getViewHolder(item);
			if (viewHolder != null) {
				viewHolder.setSeleccion(false);
			}
		}
	}

	@Override
	public boolean haySeleccionados() {
		checkAttached();
		return !seleccion.isEmpty();
	}

	@Override
	public boolean isSeleccionado(@NonNull T item) {
		checkAttached();
		return seleccion.contains(item);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] getItemsSeleccionados() {
		checkAttached();
		Class<T> clazz = listaAdapter.getClazz();
		return seleccion.toArray((T[]) Array.newInstance(clazz, 0));
	}

	@Override
	public void addOnStartSeleccionListener(@NonNull OnStartSeleccionListener onStartSeleccionListener) {
		this.onStartSeleccionListener.add(onStartSeleccionListener);
	}

	@Override
	public void addOnStopSeleccionListener(@NonNull OnStopSeleccionListener onStopSeleccionListener) {
		this.onStopSeleccionListener.add(onStopSeleccionListener);
	}

	@Override
	public void addOnSeleccionChangeListener(@NonNull OnSeleccionChangeListener onSeleccionChangeListener) {
		this.onSeleccionChangeListeners.add(onSeleccionChangeListener);
	}

	@Override
	public void addOnItemDeseleccionadoListener(@NonNull OnItemDeseleccionadoListener<T> onItemDeseleccionadoListener) {
		this.onItemDeseleccionadoListeners.add(onItemDeseleccionadoListener);
	}

	@Override
	public void addOnItemSeleccionadoListener(@NonNull OnItemSeleccionadoListener<T> onItemSeleccionadoListener) {
		this.onItemSeleccionadoListeners.add(onItemSeleccionadoListener);
	}

	public void attachLista(AbstractLista<T> lista) {
		this.listaAdapter = lista;
		this.listaAdapter.registrarObservable(observable);
	}

	public void detachLista() {
		this.listaAdapter.removerObservable(observable);
		this.listaAdapter = null;
	}

	private void startSeleccion() {
		for (int i = 0; i < onStartSeleccionListener.size(); i++) {
			OnStartSeleccionListener listener = this.onStartSeleccionListener.get(i);
			listener.onStartSeleccion();
		}
	}

	private void stopSeleccion() {
		for (int i = 0; i < onStopSeleccionListener.size(); i++) {
			OnStopSeleccionListener listener = this.onStopSeleccionListener.get(i);
			listener.onStopSeleccion();
		}
	}

	private void onSeleccionChange() {
		int size = seleccion.size();
		for (int i = 0; i < onSeleccionChangeListeners.size(); i++) {
			OnSeleccionChangeListener listener = this.onSeleccionChangeListeners.get(i);
			listener.onSeleccionChangeListener(size);
		}
	}

	private void onItemSeleccionado(T item) {
		for (OnItemSeleccionadoListener<T> onItemSeleccionadoListener : onItemSeleccionadoListeners) {
			onItemSeleccionadoListener.onItemSeleccionado(item);
		}
	}

	private void onItemDeseleccionado(T item) {
		for (OnItemDeseleccionadoListener<T> onItemDeseleccionadoListener : onItemDeseleccionadoListeners) {
			onItemDeseleccionadoListener.onItemDeseleccionado(item);
		}
	}

	private void checkAttached() {
		if (listaAdapter == null) {
			throw new IllegalStateException("No se a asociado el adapter a ninguna Lista");
		}
	}

	private class ListaSeleccionableObservable extends ListaObservable<T> {

		@Override
		protected void recargarLista() {
			removeAllSeleccion();
		}

		@Override
		protected boolean onClickElemento(AbstractViewHolder<T> viewHolder) {
			T item = viewHolder.getItem();
			boolean haySeleccionados = haySeleccionados();
			if (tipoSeleccion != ETipoSeleccion.NONE) {
				if (!haySeleccionados) {
					if (tipoSeleccion != ETipoSeleccion.LONGCLICK) {
						addSeleccion(item);
						return true;
					}
				} else {
					if (isSeleccionado(item)) {
						removeSeleccion(item);
					} else {
						if (tipoSeleccion == ETipoSeleccion.CLICK_UNICO) {
							T[] itemsSeleccionados = getItemsSeleccionados();
							T oldSeleccionado = itemsSeleccionados.length > 0 ? itemsSeleccionados[0] : null;
							removeAllSeleccionSinEventos();
							if (oldSeleccionado != null) {
								onItemDeseleccionado(oldSeleccionado);
							}
							addSeleccion(item);
						} else {
							addSeleccion(item);
						}
					}
					return true;
				}
			}
			return false;
		}

		@Override
		protected boolean onLongClickElemento(AbstractViewHolder<T> viewHolder) {
			T item = viewHolder.getItem();
			if (tipoSeleccion != ETipoSeleccion.NONE) {
				if (!haySeleccionados()) {
					addSeleccion(item);
				} else {
					if (isSeleccionado(item)) {
						removeSeleccion(item);
					} else {
						if (tipoSeleccion == ETipoSeleccion.CLICK_UNICO) {
							removeAllSeleccionSinEventos();
							addSeleccion(item);
						} else {
							addSeleccion(item);
						}
					}
				}
				return true;
			}
			return false;
		}

		@Override
		protected void setItem(T item) {
			T[] itemsSeleccionados = getItemsSeleccionados();
			T old = null;
			for (int i = 0; i < itemsSeleccionados.length && old == null; i++) {
				if (item.getId() == itemsSeleccionados[i].getId()) {
					old = itemsSeleccionados[i];
				}
			}
			if (old != null) {
				removeSeleccion(old);
				addSeleccion(item);
			}
		}

		@Override
		protected void deleteItem(T item) {
			if (isSeleccionado(item)) {
				removeSeleccion(item);
			}
		}

		@Override
		protected void onBindViewHolder(@NonNull AbstractViewHolder<T> holder, int position) {
			T item = listaAdapter.getItem(position);
			boolean seleccionado = isSeleccionado(item);
			holder.setSeleccion(seleccionado);
		}
	}
}
