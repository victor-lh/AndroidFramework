package com.victorlh.android.framework.listas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.victorlh.android.framework.listas.ItemLista;

/**
 * @author Victor
 * 17/12/2018
 */
public abstract class AbstractViewHolder<T extends ItemLista> extends RecyclerView.ViewHolder {

	@LayoutRes
	private final int layoutItem;
	@LayoutRes
	private final int layoutItemVoid;

	private ListaAdapter<T> lista;

	private T item;
	private boolean isSeleccionado;

	private ListaAdapter.OnClickListaAdapter onClickListaAdapter;

	public AbstractViewHolder(View itemView, int layoutItem) {
		this(itemView, layoutItem, 0);
	}

	public AbstractViewHolder(View itemView, int layoutItem, int layoutItemVoid) {
		super(itemView);
		this.layoutItem = layoutItem;
		this.layoutItemVoid = layoutItemVoid;
	}

	final void setLista(ListaAdapter<T> listaAdapter) {
		this.lista = listaAdapter;
	}

	public final void procesar(@NonNull T item) {
		this.item = item;

		if (itemView instanceof ViewGroup) {
			Context context = itemView.getContext();
			((ViewGroup) itemView).removeAllViews();

			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(layoutItem, (ViewGroup) itemView, false);
			ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
			itemView.setLayoutParams(layoutParams);
			((ViewGroup) itemView).addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

			if (onClickListaAdapter != null) {
				view.setOnClickListener(onClickListaAdapter);
				view.setOnLongClickListener(onClickListaAdapter);
			}
		}

		onProcesar();
	}

	public final void procesarEmpty() {
		this.item = null;
		if (itemView instanceof ViewGroup) {
			((ViewGroup) itemView).removeAllViews();

			if (layoutItemVoid != 0) {
				Context context = itemView.getContext();
				LayoutInflater inflater = LayoutInflater.from(context);
				View view = inflater.inflate(layoutItemVoid, (ViewGroup) itemView, false);

				ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
				itemView.setLayoutParams(layoutParams);
				((ViewGroup) itemView).addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			}
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

	protected ListaAdapter<T> getLista() {
		return lista;
	}

	protected int getPosicion() {
		return lista.getItemPosition(getItem());
	}

	void setOnClickListaAdapter(ListaAdapter.OnClickListaAdapter onClickListaAdapter) {
		this.onClickListaAdapter = onClickListaAdapter;
	}
}
