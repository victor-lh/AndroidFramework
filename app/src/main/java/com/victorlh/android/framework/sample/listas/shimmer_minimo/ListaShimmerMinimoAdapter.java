package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import android.view.View;

import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.adapter.ListaAdapter;
import com.victorlh.android.framework.sample.R;

public class ListaShimmerMinimoAdapter extends ListaAdapter<ItemListaShimmerMinimo> {

	ListaShimmerMinimoAdapter() {
		super(ItemListaShimmerMinimo.class);
	}

	@Override
	protected AbstractViewHolder<ItemListaShimmerMinimo> createViewHolder(View view) {
		return new ViewHolderListaShimmerMinimo(view);
	}
}
