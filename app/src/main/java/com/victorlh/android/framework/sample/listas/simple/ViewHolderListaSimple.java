package com.victorlh.android.framework.sample.listas.simple;

import android.view.View;
import android.widget.TextView;

import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.sample.R;
import com.victorlh.tools.Transform;

public class ViewHolderListaSimple extends AbstractViewHolder<ItemListaSimple> {

	public ViewHolderListaSimple(View itemView) {
		super(itemView, R.layout.item_lista_simple);
	}

	@Override
	protected void onProcesar() {
		ItemListaSimple item = getItem();

		TextView tvLista = itemView.findViewById(R.id.tvLista);
		tvLista.setText(Transform.toString(item.getNumber()));
	}
}
