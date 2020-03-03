package com.victorlh.android.framework.listas.adapter.seleccionable

import com.victorlh.android.framework.listas.adapter.AbstractLista
import com.victorlh.android.framework.listas.adapter.seleccionable.ListaSeleccionable.*

/**
 * Created by victor on 07/07/2017.
 */
abstract class AbstractListaSeleccionable<T> protected constructor(clazz: Class<T>, tipoSeleccion: ETipoSeleccion = ETipoSeleccion.NONE) : AbstractLista<T>(clazz), ListaSeleccionable<T> {

    private val adapter: ListaSeleccionableAdapter<T> by lazy {
        ListaSeleccionableAdapter(tipoSeleccion, this)
    }

    override val tipoSeleccion: ETipoSeleccion
        get() = adapter.tipoSeleccion

    override fun setSeleccion(items: Array<T>) {
        adapter.setSeleccion(items)
    }

    override fun setSeleccion(items: List<T>) {
        adapter.setSeleccion(items)
    }

    override fun addSeleccion(item: T) {
        adapter.addSeleccion(item)
    }

    override fun removeSeleccion(item: T) {
        adapter.removeSeleccion(item)
    }

    override fun removeAllSeleccion() {
        adapter.removeAllSeleccion()
    }

    override fun haySeleccionados(): Boolean {
        return adapter.haySeleccionados()
    }

    override fun isSeleccionado(item: T): Boolean {
        return adapter.isSeleccionado(item)
    }

    override val itemsSeleccionados: Array<T>
        get() = adapter.itemsSeleccionados

    override fun addOnStartSeleccionListener(onStartSeleccionListener: OnStartSeleccionListener) {
        adapter.addOnStartSeleccionListener(onStartSeleccionListener)
    }

    override fun addOnStopSeleccionListener(onStopSeleccionListener: OnStopSeleccionListener) {
        adapter.addOnStopSeleccionListener(onStopSeleccionListener)
    }

    override fun addOnSeleccionChangeListener(onSeleccionChangeListener: OnSeleccionChangeListener) {
        adapter.addOnSeleccionChangeListener(onSeleccionChangeListener)
    }

    override fun addOnItemDeseleccionadoListener(onItemDeseleccionadoListener: OnItemDeseleccionadoListener<T>) {
        adapter.addOnItemDeseleccionadoListener(onItemDeseleccionadoListener)
    }

    override fun addOnItemSeleccionadoListener(onItemSeleccionadoListener: OnItemSeleccionadoListener<T>) {
        adapter.addOnItemSeleccionadoListener(onItemSeleccionadoListener)
    }

}