package com.victorlh.android.framework.listas.expandiblehijodinamico;

import android.view.View;

import com.victorlh.android.framework.listas.expandibles.AbstractViewHolderChild;
import com.victorlh.android.framework.listas.expandibles.ItemGroupLista;

/**
 * @author Victor
 * 18/01/2019
 */
public abstract class AbstractViewHolderHijoDinamico<IGL extends ItemGroupLista> extends AbstractViewHolderChild<HijoListaDinamica<IGL>> {

	public AbstractViewHolderHijoDinamico(View itemView) {
		super(itemView);
	}
}
