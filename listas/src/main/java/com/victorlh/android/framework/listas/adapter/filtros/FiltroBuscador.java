package com.victorlh.android.framework.listas.adapter.filtros;

import com.victorlh.android.framework.listas.filtros.ItemListaBuscador;
import com.victorlh.tools.ToolsValidacion;

public class FiltroBuscador<T extends ItemListaBuscador> implements FiltroLista<T> {

	private String text;

	public void setText(String text) {
		text = text == null ? "" : text;
		this.text = text.toUpperCase();
	}

	@Override
	public boolean isDentroFiltro(T elemento) {
		String textoBuscador = elemento.getTextoBuscador();
		if (ToolsValidacion.isCadenaNoVacia(textoBuscador)) {
			String upperCase = textoBuscador.toUpperCase();
			return upperCase.contains(text);
		}
		return false;
	}
}
