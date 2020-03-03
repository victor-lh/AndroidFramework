package com.victorlh.android.framework.listas.adapter.filtros

import com.victorlh.android.framework.listas.ItemLista
import com.victorlh.android.framework.listas.adapter.AbstractLista
import java.util.*

class ListaFiltrableAdapter<T : ItemLista?>(protected val adapter: AbstractLista<T>) : ListaFiltrable<T> {

    protected var listaOriginal: List<T>? = null
    protected val filtros: MutableSet<FiltroLista<T>> = HashSet()

    override fun filtrar() {
        if (listaOriginal == null) {
            listaOriginal = adapter.getLista()
        }
        if (filtros.isEmpty()) {
            adapter.setLista(listaOriginal!!)
            listaOriginal = null
        } else {
            val filtrados = ArrayList<T>()
            for (elemento in listaOriginal!!) {
                if (isDentroFiltros(elemento)) {
                    filtrados.add(elemento)
                }
            }
            adapter.setLista(filtrados)
        }
    }

    override fun invalidate() {
        listaOriginal = null
    }

    override fun addFiltro(filtro: FiltroLista<T>) {
        filtros.add(filtro)
    }

    override fun removeFiltro(filtro: FiltroLista<T>) {
        filtros.remove(filtro)
    }

    override fun removeAllFiltros() {
        filtros.clear()
    }

    private fun isDentroFiltros(elemento: T): Boolean {
        for (filtro in filtros) {
            val dentroFiltro = filtro.isDentroFiltro(elemento)
            if (!dentroFiltro) {
                return false
            }
        }
        return true
    }
}