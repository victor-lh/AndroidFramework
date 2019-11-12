package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.sample.R;
import com.victorlh.tools.Transform;

public class ViewHolderListaShimmerMinimo extends AbstractViewHolder<ItemListaShimmerMinimo> {

	public ViewHolderListaShimmerMinimo(View itemView) {
		super(itemView, R.layout.item_lista_shimmer_minimo, R.layout.item_lista_void);
	}

	@Override
	protected void onProcesar() {
		ItemListaShimmerMinimo item = getItem();

		TextView tvLista = itemView.findViewById(R.id.tvLista);
		tvLista.setText(Transform.toString(item.getNumber()));
	}

	@Override
	public void onSelect() {
		TextView tvLista = itemView.findViewById(R.id.tvLista);
		tvLista.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent));
//		tvLista.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.wh));
	}

	@Override
	public void onUnselect() {
		TextView tvLista = itemView.findViewById(R.id.tvLista);
		tvLista.setBackgroundColor(Color.parseColor("#FFFFFF"));
	}
}
