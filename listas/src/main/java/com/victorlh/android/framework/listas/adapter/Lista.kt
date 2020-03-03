package com.victorlh.android.framework.listas.adapter

interface Lista<T> {

    fun getItemId(position: Int): Long
    fun getLista(): List<T>
    fun getItem(position: Int): T
    fun getItemCount(): Int
    fun getItemPosition(item: T): Int
    fun getItemPosition(item: T, byId: Boolean): Int
    fun getItemById(id: Long): T?
    fun setLista(lista: Array<T>)
    fun setLista(lista: List<T>)
    fun addItem(item: T)
    fun addItem(item: T, position: Int)
    fun setItem(item: T)
    fun deleteItem(item: T)
    fun recargarLista()

    fun setOnClickElementoListener(onClickElementoListener: OnClickElementoListener<T>)
    fun setOnLongClickElementoListener(onLongClickElementoListener: OnLongClickElementoListener<T>)
    fun setOnDataListChangeListener(onDataListChangeListener: OnDataListChangeListener)

    interface OnClickElementoListener<T> {
        fun onClickElemento(viewHolder: AbstractViewHolder<T>)
    }

    interface OnLongClickElementoListener<T> {
        fun onLongClickElemento(viewHolder: AbstractViewHolder<T>): Boolean
    }

    interface OnDataListChangeListener {
        fun onSetData()
        fun onVoidData()
        fun onChangeData()
    }
}