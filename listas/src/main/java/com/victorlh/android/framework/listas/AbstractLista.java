package com.victorlh.android.framework.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.victorlh.tools.ordenacion.ElementoOrdenado;
import com.victorlh.tools.ordenacion.KeyOrdenacion;
import com.victorlh.tools.ordenacion.UtilOrdenacion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by victor on 07/07/2017.
 *
 * @param <T>
 * @deprecated User {@link com.victorlh.android.framework.listas.adapter.AbstractLista}
 */
@Deprecated()
public abstract class AbstractLista<T extends ItemLista> extends RecyclerView.Adapter<AbstractViewHolder<T>> {

	public enum ETipoSeleccion {
		NONE, CLICK_UNICO, CLICK_MULTIPLE, LONGCLICK
	}

	private int layout;
	private Class<T> clazz;

	private List<T> lista;

	private ETipoSeleccion tipoSeleccion;
	private HashMap<T, AbstractViewHolder<T>> viewHolders;
	private List<T> seleccion;

	private IEventosListas.OnClickElementoListener<T> onClickElementoListener;
	private IEventosListas.OnLongClickElementoListener<T> onLongClickElementoListener;
	private IEventosListas.OnDataListChangeListener onDataListChangeListener;

	private List<IEventosListas.OnStartSeleccionListener> onStartSeleccionListener;
	private List<IEventosListas.OnStopSeleccionListener> onStopSeleccionListener;
	private List<IEventosListas.OnSeleccionChangeListener> onSeleccionChangeListeners;

	private KeyOrdenacion keyOrdenacion;

	protected AbstractLista(int layout, Class<T> clazz) {
		this(layout, clazz, ETipoSeleccion.NONE);
	}

	protected AbstractLista(int layout, Class<T> clazz, ETipoSeleccion tipoSeleccion) {
		this.layout = layout;
		this.clazz = clazz;
		this.tipoSeleccion = tipoSeleccion == null ? ETipoSeleccion.NONE : tipoSeleccion;

		this.lista = new ArrayList<>();
		this.seleccion = new ArrayList<>();
		this.viewHolders = new HashMap<>();
		this.onStartSeleccionListener = new ArrayList<>();
		this.onStopSeleccionListener = new ArrayList<>();
		this.onSeleccionChangeListeners = new ArrayList<>();
	}

	public void setTipoSeleccion(ETipoSeleccion tipoSeleccion) {
		this.tipoSeleccion = tipoSeleccion == null ? ETipoSeleccion.NONE : tipoSeleccion;
		if (tipoSeleccion == ETipoSeleccion.NONE && haySeleccionados()) {
			removeAllSeleccion();
		}
	}

	public ETipoSeleccion getTipoSeleccion() {
		return tipoSeleccion;
	}

	public void setKeyOrdenacion(KeyOrdenacion keyOrdenacion) {
		this.keyOrdenacion = keyOrdenacion;
	}

	protected abstract AbstractViewHolder<T> createViewHolder(View view);

	@NonNull
	@Override
	public AbstractViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(layout, parent, false);
		AbstractViewHolder<T> viewHolder = createViewHolder(view);
		view.setTag(viewHolder);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull final AbstractViewHolder<T> holder, int position) {
		final T item = getItem(position);
		viewHolders.put(item, holder);
		boolean seleccionado = isSeleccionado(item);
		holder.procesar(this, item, seleccionado);

		View itemView = holder.itemView;

		OnClickListaListener listener = new OnClickListaListener(holder);
		itemView.setOnClickListener(listener);
		itemView.setOnLongClickListener(listener);
	}

	@Override
	public long getItemId(int position) {
		T item = getItem(position);
		return item == null ? super.getItemId(position) : item.getId();
	}

	public List<T> getLista() {
		return new ArrayList<>(lista);
	}

	public T getItem(int position) {
		if (position < 0 || position > getItemCount()) {
			return null;
		}
		return lista.get(position);
	}

	@Override
	public int getItemCount() {
		return lista.size();
	}

	public int getItemPosition(T item) {
		return getItemPosition(item, false);
	}

	public int getItemPosition(T item, boolean byId) {
		int i = lista.indexOf(item);
		if (i == -1 && byId) {
			long id = item.getId();
			T aux = getItemById(id);
			if (aux != null) {
				i = lista.indexOf(aux);
			}
		}
		return i;
	}

	public T getItemById(long id) {
		for (T item : lista) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	public void setLista(T[] lista) {
		List<T> ts = Arrays.asList(lista);
		setLista(ts);
	}

	@SuppressWarnings("unchecked")
	public void setLista(List<T> lista) {
		if (keyOrdenacion != null && ElementoOrdenado.class.isAssignableFrom(clazz)) {
			UtilOrdenacion.ordenar((List<? extends ElementoOrdenado>) lista, keyOrdenacion);
		}
		boolean empty = this.lista.isEmpty();
		this.lista.clear();
		this.lista.addAll(lista);
		recargarLista();
		if (onDataListChangeListener != null) {
			if (empty && !this.lista.isEmpty()) {
				onDataListChangeListener.onSetData();
			} else if (!empty && this.lista.isEmpty()) {
				onDataListChangeListener.onVoidData();
			} else {
				onDataListChangeListener.onChangeData();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void addItem(T item) {
		if (keyOrdenacion != null && ElementoOrdenado.class.isAssignableFrom(clazz)) {
			List<T> aux = getLista();
			aux.add(item);
			UtilOrdenacion.ordenar((List<? extends ElementoOrdenado>) aux, keyOrdenacion);
			int position = aux.indexOf(item);
			addItem(item, position);
		} else {
			addItem(item, this.lista.size());
		}
	}

	public void addItem(T item, int position) {
		boolean empty = this.lista.isEmpty();
		if (position == lista.size()) {
			lista.add(item);
		} else {
			lista.add(position, item);
		}
		notifyItemInserted(position);
		if (onDataListChangeListener != null) {
			if (empty) {
				onDataListChangeListener.onSetData();
			} else {
				onDataListChangeListener.onChangeData();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void setItem(T item) {
		int itemPosition = getItemPosition(item, true);
		if (itemPosition == -1) {
			addItem(item);
		} else {
			T aux = getItem(itemPosition);
			boolean seleccionado = isSeleccionado(aux);
			if (seleccionado) {
				removeSeleccion(aux);
			}
			lista.set(itemPosition, item);
			if (seleccionado) {
				addSeleccion(item);
			}

			notifyItemChanged(itemPosition);
			if (keyOrdenacion != null && ElementoOrdenado.class.isAssignableFrom(clazz)) {
				List<T> auxList = getLista();
				UtilOrdenacion.ordenar((List<? extends ElementoOrdenado>) auxList, keyOrdenacion);
				int destino = auxList.indexOf(item);
				if (itemPosition != destino) {
					this.lista = auxList;
					notifyItemMoved(itemPosition, destino);
				}
			}
			if (onDataListChangeListener != null) {
				onDataListChangeListener.onChangeData();
			}
		}
	}

	public void deleteItem(T item) {
		int itemPosition = getItemPosition(item, true);
		if (itemPosition != -1) {
			T aux = getItem(itemPosition);
			removeSeleccion(aux);
			lista.remove(itemPosition);
			notifyItemRemoved(itemPosition);
			if (onDataListChangeListener != null && lista.isEmpty()) {
				onDataListChangeListener.onVoidData();
			}
		}
	}

	public void recargarLista() {
		this.viewHolders.clear();
		removeAllSeleccion();
		notifyDataSetChanged();
	}

	public void setSeleccion(T[] items) {
		setSeleccion(Arrays.asList(items));
	}

	public void setSeleccion(List<T> items) {
		removeAllSeleccion();
		for (T item : items) {
			T elementoLista = getItemById(item.getId());
			if (elementoLista != null) {
				addSeleccion(elementoLista);
			}
		}
	}

	public void addSeleccion(T item) {
		boolean haySeleccionados = haySeleccionados();
		seleccion.add(item);
		AbstractViewHolder<T> viewHolder = getViewHolder(item);
		if (viewHolder != null) {
			viewHolder.setSeleccion(true);
		}
		if (!haySeleccionados) {
			startSeleccion();
		}
		onSeleccionChange();
	}

	public void removeSeleccion(T item) {
		seleccion.remove(item);
		AbstractViewHolder<T> viewHolder = getViewHolder(item);
		if (viewHolder != null) {
			viewHolder.setSeleccion(false);
		}
		onSeleccionChange();
		if (!haySeleccionados()) {
			stopSeleccion();
		}
	}

	public void removeAllSeleccion() {
		seleccion.clear();
		Collection<AbstractViewHolder<T>> values = viewHolders.values();
		for (AbstractViewHolder<T> viewHolder : values) {
			viewHolder.setSeleccion(false);
		}
		onSeleccionChange();
		stopSeleccion();
	}

	public boolean haySeleccionados() {
		return !seleccion.isEmpty();
	}

	public boolean isSeleccionado(T item) {
		return seleccion.contains(item);
	}

	@SuppressWarnings("unchecked")
	public T[] getItemsSeleccionados() {
		return seleccion.toArray((T[]) Array.newInstance(clazz, 0));
	}

	public void setOnClickElementoListener(IEventosListas.OnClickElementoListener<T> onClickElementoListener) {
		this.onClickElementoListener = onClickElementoListener;
	}

	public void setOnLongClickElementoListener(IEventosListas.OnLongClickElementoListener<T> onLongClickElementoListener) {
		this.onLongClickElementoListener = onLongClickElementoListener;
	}

	public void addOnStartSeleccionListener(IEventosListas.OnStartSeleccionListener onStartSeleccionListener) {
		this.onStartSeleccionListener.add(onStartSeleccionListener);
	}

	public void addOnStopSeleccionListener(IEventosListas.OnStopSeleccionListener onStopSeleccionListener) {
		this.onStopSeleccionListener.add(onStopSeleccionListener);
	}

	public void addOnSeleccionChangeListener(IEventosListas.OnSeleccionChangeListener onSeleccionChangeListener) {
		this.onSeleccionChangeListeners.add(onSeleccionChangeListener);
	}

	public void setOnDataListChangeListener(IEventosListas.OnDataListChangeListener onDataListChangeListener) {
		this.onDataListChangeListener = onDataListChangeListener;
	}

	Class<T> getItemClass() {
		return clazz;
	}

	private void startSeleccion() {
		for (int i = 0; i < onStartSeleccionListener.size(); i++) {
			IEventosListas.OnStartSeleccionListener listener = AbstractLista.this.onStartSeleccionListener.get(i);
			listener.onStartSeleccion();
		}
	}

	private void stopSeleccion() {
		for (int i = 0; i < onStopSeleccionListener.size(); i++) {
			IEventosListas.OnStopSeleccionListener listener = AbstractLista.this.onStopSeleccionListener.get(i);
			listener.onStopSeleccion();
		}
	}

	private void onSeleccionChange() {
		int size = seleccion.size();
		for (int i = 0; i < onSeleccionChangeListeners.size(); i++) {
			IEventosListas.OnSeleccionChangeListener listener = AbstractLista.this.onSeleccionChangeListeners.get(i);
			listener.onSeleccionChangeListener(size);
		}
	}

	@Nullable
	protected final AbstractViewHolder<T> getViewHolder(T item) {
		return viewHolders.get(item);
	}

	private final class OnClickListaListener implements View.OnClickListener, View.OnLongClickListener {

		private AbstractViewHolder<T> holder;

		private OnClickListaListener(AbstractViewHolder<T> holder) {
			this.holder = holder;
		}

		@Override
		public void onClick(View v) {
			T item = holder.getItem();
			if (tipoSeleccion == ETipoSeleccion.NONE) {
				onClick();
			} else if (!haySeleccionados()) {
				if (tipoSeleccion == ETipoSeleccion.LONGCLICK) {
					onClick();
				} else {
					addSeleccion(item);
				}
			} else {
				if (isSeleccionado(item)) {
					removeSeleccion(item);
				} else {
					if (tipoSeleccion == ETipoSeleccion.CLICK_UNICO) {
						removeAllSeleccion();
						addSeleccion(item);
					} else {
						addSeleccion(item);
					}
				}
			}
		}

		@Override
		public boolean onLongClick(View v) {
			T item = holder.getItem();
			if (tipoSeleccion == ETipoSeleccion.NONE) {
				return onLongClick();
			} else if (!haySeleccionados()) {
				addSeleccion(item);
			} else {
				if (isSeleccionado(item)) {
					removeSeleccion(item);
				} else {
					if (tipoSeleccion == ETipoSeleccion.CLICK_UNICO) {
						removeAllSeleccion();
						addSeleccion(item);
					} else {
						addSeleccion(item);
					}
				}
			}
			return true;
		}

		private void onClick() {
			if (onClickElementoListener != null) {
				onClickElementoListener.onClickElemento(holder);
			}
		}

		private boolean onLongClick() {
			if (onLongClickElementoListener != null) {
				return onLongClickElementoListener.onLongClickElemento(holder);
			}
			return false;
		}
	}
}
