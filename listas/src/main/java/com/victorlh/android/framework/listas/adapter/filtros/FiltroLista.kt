package com.victorlh.android.framework.listas.adapter.filtros

interface FiltroLista<T> {
    fun isDentroFiltro(elemento: T): Boolean
}