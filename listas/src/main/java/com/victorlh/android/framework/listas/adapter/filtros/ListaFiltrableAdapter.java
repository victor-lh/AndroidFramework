package com.victorlh.android.framework.listas.adapter.filtros;

import com.victorlh.android.framework.listas.ItemLista;
import com.victorlh.android.framework.listas.adapter.AbstractLista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListaFiltrableAdapter<T extends ItemLista> implements ListaFiltrable<T> {

	protected AbstractLista<T> adapter;
	protected List<T> listaOriginal;
	protected final Set<FiltroLista<T>> filtros;

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

	@Override
	public void invalidate() {
		this.listaOriginal = null;
	}


	@Override
	public void addFiltro(FiltroLista<T> filtro) {
		this.filtros.add(filtro);
	}

	@Override
	public void removeFiltro(FiltroLista<T> filtro) {
		this.filtros.remove(filtro);
	}

	@Override
	public void removeAllFiltros() {
		this.filtros.clear();
	}

	private boolean isDentroFiltros(T elemento) {
		for (FiltroLista<T> filtro : filtros) {
			boolean dentroFiltro = filtro.isDentroFiltro(elemento);
			if (!dentroFiltro) {
				return false;
			}
		}
		return true;
	}

}
