package com.victorlh.android.framework.listas.view;

import android.view.View;

import com.victorlh.android.framework.listas.AbstractLista;
import com.victorlh.android.framework.listas.AbstractViewHolder;
import com.victorlh.android.framework.listas.ItemLista;

class ListaVaciaAdapter extends AbstractLista<ItemLista> {

	ListaVaciaAdapter(int layout) {
		super(layout, ItemLista.class);
	}

	@Override
	protected AbstractViewHolder<ItemLista> createViewHolder(View view) {
		return new ViewHolder(view);
	}

	private static final class ViewHolder extends AbstractViewHolder<ItemLista> {

		private ViewHolder(View itemView) {
			super(itemView);
		}

		@Override
		protected void onProcesar() {
		}
	}
}
