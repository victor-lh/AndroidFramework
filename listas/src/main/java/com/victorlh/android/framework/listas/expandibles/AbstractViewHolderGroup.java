package com.victorlh.android.framework.listas.expandibles;

import android.view.View;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

/**
 * @author Victor
 * 17/12/2018
 */
public abstract class AbstractViewHolderGroup<IGL extends ItemGroupLista<ICL>, ICL extends ItemChildLista> extends AbstractExpandableItemViewHolder {

	private IGL item;
	private int position;

	public AbstractViewHolderGroup(View itemView) {
		super(itemView);
	}

	public final void procesar(IGL item, int position) {
		this.item = item;
		this.position = position;
		onProcesar();
	}

	public IGL getItem() {
		return item;
	}

	protected abstract void onProcesar();

	public int getItemPosition() {
		return position;
	}
}
