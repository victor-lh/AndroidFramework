package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import android.view.View;

import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.adapter.seleccionable.AbstractListaSeleccionable;

public class AbstractListaShimmerMinimo extends AbstractListaSeleccionable<ItemListaShimmerMinimo> {

	AbstractListaShimmerMinimo() {
		super(ItemListaShimmerMinimo.class, ETipoSeleccion.LONGCLICK);
	}

	@Override
	protected AbstractViewHolder<ItemListaShimmerMinimo> createViewHolder(View view) {
		return new ViewHolderListaShimmerMinimo(view);
	}
}
