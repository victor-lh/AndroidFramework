package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import android.view.View;

import com.victorlh.android.framework.listas.AbstractLista;
import com.victorlh.android.framework.listas.AbstractViewHolder;
import com.victorlh.android.framework.sample.R;

public class ListaShimmerMinimoAdapter extends AbstractLista<ItemListaShimmerMinimo> {

	ListaShimmerMinimoAdapter() {
		super(R.layout.item_lista_shimmer_minimo, ItemListaShimmerMinimo.class);
	}

	@Override
	protected AbstractViewHolder<ItemListaShimmerMinimo> createViewHolder(View view) {
		return new ViewHolderListaShimmerMinimo(view);
	}
}
