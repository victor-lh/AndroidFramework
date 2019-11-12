package com.victorlh.android.framework.sample.listas.simple;

import android.view.View;

import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.adapter.AbstractLista;

public class AbstractListaSimple extends AbstractLista<ItemListaSimple> {

	AbstractListaSimple() {
		super(ItemListaSimple.class);
	}

	@Override
	protected AbstractViewHolder<ItemListaSimple> createViewHolder(View view) {
		return new ViewHolderListaSimple(view);
	}
}
