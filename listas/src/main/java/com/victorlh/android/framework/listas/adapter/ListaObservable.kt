package com.victorlh.android.framework.listas.adapter

abstract class ListaObservable<T> {

    open fun setItem(item: T) {}
    open fun deleteItem(item: T) {}
    open fun recargarLista() {}
    open fun onClickElemento(viewHolder: AbstractViewHolder<T>): Boolean = false
    open fun onLongClickElemento(viewHolder: AbstractViewHolder<T>): Boolean = false
    open fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {}
}