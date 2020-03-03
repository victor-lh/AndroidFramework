package com.victorlh.android.framework.listas.adapter.filtros

interface ListaFiltrable<T> {
    fun filtrar()
    fun addFiltro(filtro: FiltroLista<T>)
    fun removeFiltro(filtro: FiltroLista<T>)
    fun removeAllFiltros()
    fun invalidate()
}