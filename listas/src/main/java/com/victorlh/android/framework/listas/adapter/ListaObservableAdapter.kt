package com.victorlh.android.framework.listas.adapter

import java.util.*

internal class ListaObservableAdapter<T> : ListaObservable<T>() {

    private val lista: MutableList<ListaObservable<T>>

    fun registrarObservable(observable: ListaObservable<T>) {
        lista.add(observable)
    }

    fun removerObservable(observable: ListaObservable<T>?) {
        lista.remove(observable)
    }

    override fun setItem(item: T) {
        for (i in lista.indices.reversed()) {
            val observable = lista[i]
            observable.setItem(item)
        }
    }

    override fun deleteItem(item: T) {
        for (i in lista.indices.reversed()) {
            val observable = lista[i]
            observable.deleteItem(item)
        }
    }

    override fun recargarLista() {
        for (i in lista.indices.reversed()) {
            val observable = lista[i]
            observable.recargarLista()
        }
    }

    override fun onClickElemento(viewHolder: AbstractViewHolder<T>): Boolean {
        var interceptado = false
        for (i in lista.indices.reversed()) {
            val observable = lista[i]
            val aux = observable.onClickElemento(viewHolder)
            if (aux) {
                interceptado = true
            }
        }
        return interceptado
    }

    override fun onLongClickElemento(viewHolder: AbstractViewHolder<T>): Boolean {
        var interceptado = false
        for (i in lista.indices.reversed()) {
            val observable = lista[i]
            val aux = observable.onLongClickElemento(viewHolder)
            if (aux) {
                interceptado = true
            }
        }
        return interceptado
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
        for (i in lista.indices.reversed()) {
            val observable = lista[i]
            observable.onBindViewHolder(holder, position)
        }
    }

    init {
        lista = ArrayList()
    }
}