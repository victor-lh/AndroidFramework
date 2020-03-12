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

    fun setOnClickElementoListener(onClickElementoListener: (viewHolder: AbstractViewHolder<T>) -> Unit)
    fun setOnLongClickElementoListener(onLongClickElementoListener: (viewHolder: AbstractViewHolder<T>) -> Boolean)
    fun setOnDataListChangeListener(onDataListChangeListener: OnDataListChangeListener)

    interface OnDataListChangeListener {
        fun onSetData()
        fun onVoidData()
        fun onChangeData()
    }
}