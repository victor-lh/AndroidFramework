package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import android.view.View;
import android.widget.TextView;

import com.victorlh.android.framework.listas.AbstractViewHolder;
import com.victorlh.android.framework.sample.R;
import com.victorlh.tools.Transform;

public class ViewHolderListaShimmerMinimo extends AbstractViewHolder<ItemListaShimmerMinimo> {

	public ViewHolderListaShimmerMinimo(View itemView) {
		super(itemView);
	}

	@Override
	protected void onProcesar() {
		ItemListaShimmerMinimo item = getItem();

		TextView tvLista = itemView.findViewById(R.id.tvLista);
		tvLista.setText(Transform.toString(item.getNumber()));
	}
}
