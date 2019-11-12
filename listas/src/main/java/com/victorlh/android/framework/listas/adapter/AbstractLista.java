package com.victorlh.android.framework.listas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.victorlh.android.framework.listas.ItemLista;
import com.victorlh.android.framework.listas.R;
import com.victorlh.tools.ordenacion.ElementoOrdenado;
import com.victorlh.tools.ordenacion.KeyOrdenacion;
import com.victorlh.tools.ordenacion.UtilOrdenacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by victor on 07/07/2017.
 */
public abstract class AbstractLista<T extends ItemLista> extends RecyclerView.Adapter<AbstractViewHolder<T>> implements Lista<T> {

	private Class<T> clazz;

	private List<T> lista;

	private HashMap<T, AbstractViewHolder<T>> viewHolders;

	private OnClickElementoListener<T> onClickElementoListener;
	private OnLongClickElementoListener<T> onLongClickElementoListener;
	private OnDataListChangeListener onDataListChangeListener;

	private KeyOrdenacion keyOrdenacion;

	private ListaObservableAdapter<T> observableAdapter;

	protected AbstractLista(Class<T> clazz) {
		this.clazz = clazz;

		this.lista = new ArrayList<>();
		this.viewHolders = new HashMap<>();

		this.observableAdapter = new ListaObservableAdapter<>();
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
		View itemView = inflater.inflate(R.layout.item_listas_wrapper, parent, false);

		AbstractViewHolder<T> viewHolder = createViewHolder(itemView);
		viewHolder.setLista(this);
		itemView.setTag(viewHolder);
		return viewHolder;
	}

	@Override
	@CallSuper
	public void onBindViewHolder(@NonNull final AbstractViewHolder<T> holder, int position) {
		OnClickListaAdapter listener = new OnClickListaAdapter(holder);
		holder.setOnClickListaAdapter(listener);

		final T item = getItem(position);
		viewHolders.put(item, holder);
		holder.procesar(item);
		observableAdapter.onBindViewHolder(holder, position);
	}

	@Override
	public long getItemId(int position) {
		T item = getItem(position);
		return item == null ? super.getItemId(position) : item.getId();
	}

	@Override
	public List<T> getLista() {
		return new ArrayList<>(lista);
	}

	@Override
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

	@Override
	public int getItemPosition(T item) {
		return getItemPosition(item, false);
	}

	@Override
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

	@Override
	public T getItemById(long id) {
		for (T item : lista) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	@Override
	public void setLista(T[] lista) {
		List<T> ts = Arrays.asList(lista);
		setLista(ts);
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
	@SuppressWarnings("unchecked")
	public void setItem(T item) {
		int itemPosition = getItemPosition(item, true);
		if (itemPosition == -1) {
			addItem(item);
		} else {
			lista.set(itemPosition, item);
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
			observableAdapter.setItem(item);
			if (onDataListChangeListener != null) {
				onDataListChangeListener.onChangeData();
			}
		}
	}

	@Override
	public void deleteItem(T item) {
		int itemPosition = getItemPosition(item, true);
		if (itemPosition != -1) {
			lista.remove(itemPosition);
			notifyItemRemoved(itemPosition);
			observableAdapter.deleteItem(item);
			if (onDataListChangeListener != null && lista.isEmpty()) {
				onDataListChangeListener.onVoidData();
			}
		}
	}

	@Override
	public void recargarLista() {
		this.viewHolders.clear();
		notifyDataSetChanged();
		observableAdapter.recargarLista();
	}

	@Override
	public void setOnClickElementoListener(OnClickElementoListener<T> onClickElementoListener) {
		this.onClickElementoListener = onClickElementoListener;
	}

	@Override
	public void setOnLongClickElementoListener(OnLongClickElementoListener<T> onLongClickElementoListener) {
		this.onLongClickElementoListener = onLongClickElementoListener;
	}

	@Override
	public void setOnDataListChangeListener(OnDataListChangeListener onDataListChangeListener) {
		this.onDataListChangeListener = onDataListChangeListener;
	}

	@Nullable
	public final AbstractViewHolder<T> getViewHolder(T item) {
		return viewHolders.get(item);
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void registrarObservable(ListaObservable<T> observable) {
		observableAdapter.registrarObservable(observable);
	}

	public void removerObservable(ListaObservable<T> observable) {
		observableAdapter.removerObservable(observable);
	}

	class OnClickListaAdapter implements View.OnClickListener, View.OnLongClickListener {

		protected final AbstractViewHolder<T> holder;

		private OnClickListaAdapter(AbstractViewHolder<T> holder) {
			this.holder = holder;
		}

		@Override
		public void onClick(View v) {
			observableAdapter.onClickElemento(holder);
			if (onClickElementoListener != null) {
				onClickElementoListener.onClickElemento(holder);
			}
		}

		@Override
		public boolean onLongClick(View v) {
			observableAdapter.onLongClickElemento(holder);
			if (onLongClickElementoListener != null) {
				return onLongClickElementoListener.onLongClickElemento(holder);
			}
			return false;
		}
	}
}
