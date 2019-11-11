package com.victorlh.android.framework.listas.view;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;

class ListaMinElementosManager {

	private final int numeroMinimoItems;

	ListaMinElementosManager(int numeroMinimoItems) {
		this.numeroMinimoItems = numeroMinimoItems;
	}

	RecyclerView.Adapter wrapAdapter(RecyclerView.Adapter listaAdapter) {
		ListaMinElementosWrapper wrapper = new ListaMinElementosWrapper(listaAdapter);
		AdapterDataObserver observer = new AdapterDataObserver(wrapper);
		listaAdapter.registerAdapterDataObserver(observer);
		return wrapper;
	}

	private class ListaMinElementosWrapper extends RecyclerView.Adapter {

		private final RecyclerView.Adapter listaAdapter;

		private ListaMinElementosWrapper(RecyclerView.Adapter listaAdapter) {
			this.listaAdapter = listaAdapter;
		}

		@NonNull
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return listaAdapter.onCreateViewHolder(parent, viewType);
		}

		@Override
		@SuppressWarnings("unchecked")
		public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
			if (position >= listaAdapter.getItemCount()) {
				if (holder instanceof AbstractViewHolder) {
					((AbstractViewHolder) holder).procesarEmpty();
				}
			} else {
				listaAdapter.onBindViewHolder(holder, position);
			}
		}

		@Override
		public int getItemCount() {
			int itemCount = getItemCountData();
			return itemCount < numeroMinimoItems ? numeroMinimoItems : itemCount;
		}

		public int getItemCountData() {
			return listaAdapter.getItemCount();
		}

		public int getItemCountVoid() {
			int itemCountData = getItemCountData();
			return itemCountData >= numeroMinimoItems ? 0 : numeroMinimoItems - itemCountData;
		}
	}

	private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {

		private final ListaMinElementosWrapper adapter;

		private AdapterDataObserver(ListaMinElementosWrapper adapter) {
			this.adapter = adapter;
		}

		public void onChanged() {
			adapter.notifyDataSetChanged();
		}

		public void onItemRangeChanged(int positionStart, int itemCount) {
			adapter.notifyDataSetChanged();
		}

		public void onItemRangeInserted(int positionStart, int itemCount) {
			adapter.notifyDataSetChanged();
		}

		public void onItemRangeRemoved(int positionStart, int itemCount) {
			adapter.notifyDataSetChanged();
		}

		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
			adapter.notifyDataSetChanged();
		}
	}
}
