package com.victorlh.android.framework.listas.expandibles;

import android.view.View;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

/**
 * @author Victor
 * 17/12/2018
 */
public abstract class AbstractViewHolderChild<ICL extends ItemChildLista> extends AbstractExpandableItemViewHolder {

	private ICL item;
	private int parentPosition;
	private int position;

	public AbstractViewHolderChild(View itemView) {
		super(itemView);
	}

	public final void procesar(ICL item, int parentPosition, int position) {
		this.item = item;
		this.parentPosition = parentPosition;
		this.position = position;
		onProcesar();
	}

	public ICL getItem() {
		return item;
	}

	protected abstract void onProcesar();

	public int getParentPosition() {
		return parentPosition;
	}

	public int getItemPosition() {
		return position;
	}
}
