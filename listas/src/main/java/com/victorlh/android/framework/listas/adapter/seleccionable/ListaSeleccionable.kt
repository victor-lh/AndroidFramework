package com.victorlh.android.framework.listas.adapter.seleccionable

interface ListaSeleccionable<T> {

    enum class ETipoSeleccion {
        NONE, CLICK_UNICO, CLICK_MULTIPLE, LONGCLICK
    }

    val tipoSeleccion: ETipoSeleccion
    val itemsSeleccionados: Array<T>

    fun setSeleccion(items: Array<T>)
    fun setSeleccion(items: List<T>)
    fun addSeleccion(item: T)
    fun removeSeleccion(item: T)
    fun removeAllSeleccion()
    fun haySeleccionados(): Boolean
    fun isSeleccionado(item: T): Boolean
    fun addOnStartSeleccionListener(onStartSeleccionListener: OnStartSeleccionListener)
    fun addOnStopSeleccionListener(onStopSeleccionListener: OnStopSeleccionListener)
    fun addOnSeleccionChangeListener(onSeleccionChangeListener: OnSeleccionChangeListener)
    fun addOnItemSeleccionadoListener(onItemSeleccionadoListener: OnItemSeleccionadoListener<T>)
    fun addOnItemDeseleccionadoListener(onItemDeseleccionadoListener: OnItemDeseleccionadoListener<T>)

    interface OnStartSeleccionListener {
        fun onStartSeleccion()
    }

    interface OnStopSeleccionListener {
        fun onStopSeleccion()
    }

    interface OnSeleccionChangeListener {
        fun onSeleccionChangeListener(numSeleccionados: Int)
    }

    interface OnItemSeleccionadoListener<T> {
        fun onItemSeleccionado(item: T)
    }

    interface OnItemDeseleccionadoListener<T> {
        fun onItemDeseleccionado(item: T)
    }
}