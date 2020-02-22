package com.victorlh.android.framework.listas.adapter.filtros

import com.victorlh.tools.ToolsValidacion
import java.util.*

class FiltroBuscador<T : ItemListaBuscador> : FiltroLista<T> {

    private var text: String? = null

    fun setText(text: String) {
        this.text = text.toUpperCase(Locale.getDefault())
    }

    override fun isDentroFiltro(elemento: T): Boolean {
        val textoBuscador = elemento.textoBuscador
        if (ToolsValidacion.isCadenaNoVacia(textoBuscador)) {
            val upperCase = textoBuscador.toUpperCase(Locale.getDefault())
            return upperCase.contains(text!!)
        }
        return false
    }
}