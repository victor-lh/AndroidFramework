package com.victorlh.android.framework.sample.listas.simple;

import android.view.View;

import com.victorlh.android.framework.listas.AbstractLista;
import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.adapter.ListaAdapter;
import com.victorlh.android.framework.sample.R;

public class ListaSimpleAdapter extends ListaAdapter<ItemListaSimple> {

	ListaSimpleAdapter() {
		super(ItemListaSimple.class);
	}

	@Override
	protected AbstractViewHolder<ItemListaSimple> createViewHolder(View view) {
		return new ViewHolderListaSimple(view);
	}
}
