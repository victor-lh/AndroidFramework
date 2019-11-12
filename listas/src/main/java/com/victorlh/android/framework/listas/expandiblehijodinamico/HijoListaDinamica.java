package com.victorlh.android.framework.listas.expandiblehijodinamico;

import com.victorlh.android.framework.listas.expandibles.ItemChildLista;
import com.victorlh.android.framework.listas.expandibles.ItemGroupLista;

/**
 * @author Victor
 * 18/01/2019
 */
public class HijoListaDinamica<IGL extends ItemGroupLista> implements ItemChildLista  {

	private IGL item;

	public HijoListaDinamica(IGL item) {
		this.item = item;
	}

	@Override
	public long getId() {
		return 0;
	}

	public IGL getItem() {
		return item;
	}
}
