package com.victorlh.android.framework.listas.filtros;

import com.victorlh.android.framework.listas.AbstractLista;
import com.victorlh.android.framework.listas.ItemLista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListaFiltrableAdapter<T extends ItemLista> implements IListaFiltrable<T> {

	protected AbstractLista<T> adapter;
	protected List<T> listaOriginal;
	protected final Set<IFiltroLista<T>> filtros;

	public ListaFiltrableAdapter(AbstractLista<T> adapter) {
		this.filtros = new HashSet<>();
		this.adapter = adapter;
	}

	public void filtrar() {
		if (listaOriginal == null) {
			listaOriginal = adapter.getLista();
		}

		if (filtros.isEmpty()) {
			adapter.setLista(listaOriginal);
			listaOriginal = null;
		} else {
			ArrayList<T> filtrados = new ArrayList<>();
			for (T elemento : listaOriginal) {
				if (isDentroFiltros(elemento)) {
					filtrados.add(elemento);
				}
			}
			adapter.setLista(filtrados);
		}
	}

	public void invalidate() {
		this.listaOriginal = null;
	}


	@Override
	public void addFiltro(IFiltroLista<T> filtro) {
		this.filtros.add(filtro);
	}

	@Override
	public void removeFiltro(IFiltroLista<T> filtro) {
		this.filtros.remove(filtro);
	}

	@Override
	public void removeAllFiltros() {
		this.filtros.clear();
	}

	private boolean isDentroFiltros(T elemento) {
		for (IFiltroLista<T> filtro : filtros) {
			boolean dentroFiltro = filtro.isDentroFiltro(elemento);
			if (!dentroFiltro) {
				return false;
			}
		}
		return true;
	}

}
