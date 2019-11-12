package com.victorlh.android.framework.listas.expandiblehijodinamico;

import com.victorlh.android.framework.listas.expandibles.AbstractListaExpandible;
import com.victorlh.android.framework.listas.expandibles.AbstractViewHolderGroup;
import com.victorlh.android.framework.listas.expandibles.ItemGroupLista;

/**
 * @author Victor
 * 18/01/2019
 */
public abstract class AbstractListaExpandibleDinamic<
		IGL extends ItemGroupLista<HijoListaDinamica<IGL>>,
		GVH extends AbstractViewHolderGroup<IGL, HijoListaDinamica<IGL>>,
		CVH extends AbstractViewHolderHijoDinamico<IGL>> extends
		AbstractListaExpandible<IGL, HijoListaDinamica<IGL>, GVH, CVH> {

	public AbstractListaExpandibleDinamic(int lyGroupId, int lyChildId) {
		super(lyGroupId, lyChildId);
	}
}
