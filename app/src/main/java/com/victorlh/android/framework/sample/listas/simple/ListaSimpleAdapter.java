package com.victorlh.android.framework.sample.listas.simple;

import android.view.View;

import com.victorlh.android.framework.listas.AbstractLista;
import com.victorlh.android.framework.listas.AbstractViewHolder;
import com.victorlh.android.framework.sample.R;

public class ListaSimpleAdapter extends AbstractLista<ItemListaSimple> {

	ListaSimpleAdapter() {
		super(R.layout.item_lista_simple, ItemListaSimple.class);
	}

	@Override
	protected AbstractViewHolder<ItemListaSimple> createViewHolder(View view) {
		return new ViewHolderListaSimple(view);
	}
}
