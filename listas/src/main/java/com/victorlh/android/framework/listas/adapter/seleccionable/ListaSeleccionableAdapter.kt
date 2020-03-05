package com.victorlh.android.framework.listas.adapter.seleccionable

import com.victorlh.android.framework.listas.adapter.AbstractLista
import com.victorlh.android.framework.listas.adapter.AbstractViewHolder
import com.victorlh.android.framework.listas.adapter.ListaObservable
import com.victorlh.android.framework.listas.adapter.seleccionable.ListaSeleccionable.*
import java.util.*

open class ListaSeleccionableAdapter<T>(override val tipoSeleccion: ETipoSeleccion, val listaAdapter: AbstractLista<T>) : ListaSeleccionable<T> {

    private val observable = ListaSeleccionableObservable()
    private val seleccion = ArrayList<T>()
    private val onStartSeleccionListener: MutableList<OnStartSeleccionListener> = ArrayList()
    private val onStopSeleccionListener: MutableList<OnStopSeleccionListener> = ArrayList()
    private val onSeleccionChangeListeners: MutableList<OnSeleccionChangeListener> = ArrayList()
    private val onItemSeleccionadoListeners: MutableList<OnItemSeleccionadoListener<T>> = ArrayList()
    private val onItemDeseleccionadoListeners: MutableList<OnItemDeseleccionadoListener<T>> = ArrayList()

    init {
        listaAdapter.registrarObservable(observable)
    }

    override fun setSeleccion(items: Array<T>) {
        setSeleccion(listOf(*items))
    }

    override fun setSeleccion(items: List<T>) {
        removeAllSeleccion()
        for (item in items) {
            val id = listaAdapter.getItemId(item)
            val elementoLista = listaAdapter.getItemById(id)
            elementoLista?.let { addSeleccion(it) }
        }
    }

    override fun addSeleccion(item: T) {
        val haySeleccionados = haySeleccionados()
        seleccion.add(item)
        val viewHolder = listaAdapter.getViewHolder(item)
        viewHolder?.seleccionado = true
        if (!haySeleccionados) {
            startSeleccion()
        }
        onItemSeleccionado(item)
        onSeleccionChange()
    }

    override fun removeSeleccion(item: T) {
        seleccion.remove(item)
        val viewHolder = listaAdapter.getViewHolder(item)
        viewHolder?.seleccionado = false
        onItemDeseleccionado(item)
        onSeleccionChange()
        if (!haySeleccionados()) {
            stopSeleccion()
        }
    }

    override fun removeAllSeleccion() {
        seleccion.clear()
        val lista: List<T> = listaAdapter.getLista()
        for (item in lista) {
            val viewHolder = listaAdapter.getViewHolder(item)
            viewHolder?.seleccionado = false
            onItemDeseleccionado(item)
        }
        onSeleccionChange()
        stopSeleccion()
    }

    private fun removeAllSeleccionSinEventos() {
        seleccion.clear()
        val lista: List<T> = listaAdapter.getLista()
        for (item in lista) {
            val viewHolder = listaAdapter.getViewHolder(item)
            viewHolder?.seleccionado = false
        }
    }

    override fun haySeleccionados(): Boolean = seleccion.isNotEmpty()
    override fun isSeleccionado(item: T): Boolean = seleccion.contains(item)

    @Suppress("UNCHECKED_CAST")
    override val itemsSeleccionados: Array<T>
        get() = seleccion.toArray(java.lang.reflect.Array.newInstance(listaAdapter.clazz, 0) as Array<T>)

    override fun addOnStartSeleccionListener(onStartSeleccionListener: OnStartSeleccionListener) {
        this.onStartSeleccionListener.add(onStartSeleccionListener)
    }

    override fun addOnStopSeleccionListener(onStopSeleccionListener: OnStopSeleccionListener) {
        this.onStopSeleccionListener.add(onStopSeleccionListener)
    }

    override fun addOnSeleccionChangeListener(onSeleccionChangeListener: OnSeleccionChangeListener) {
        onSeleccionChangeListeners.add(onSeleccionChangeListener)
    }

    override fun addOnItemDeseleccionadoListener(onItemDeseleccionadoListener: OnItemDeseleccionadoListener<T>) {
        onItemDeseleccionadoListeners.add(onItemDeseleccionadoListener)
    }

    override fun addOnItemSeleccionadoListener(onItemSeleccionadoListener: OnItemSeleccionadoListener<T>) {
        onItemSeleccionadoListeners.add(onItemSeleccionadoListener)
    }

    private fun startSeleccion() {
        for (i in onStartSeleccionListener.indices) {
            val listener = onStartSeleccionListener[i]
            listener.onStartSeleccion()
        }
    }

    private fun stopSeleccion() {
        for (i in onStopSeleccionListener.indices) {
            val listener = onStopSeleccionListener[i]
            listener.onStopSeleccion()
        }
    }

    private fun onSeleccionChange() {
        val size = seleccion.size
        for (i in onSeleccionChangeListeners.indices) {
            val listener = onSeleccionChangeListeners[i]
            listener.onSeleccionChangeListener(size)
        }
    }

    private fun onItemSeleccionado(item: T) {
        for (onItemSeleccionadoListener in onItemSeleccionadoListeners) {
            onItemSeleccionadoListener.onItemSeleccionado(item)
        }
    }

    private fun onItemDeseleccionado(item: T) {
        for (onItemDeseleccionadoListener in onItemDeseleccionadoListeners) {
            onItemDeseleccionadoListener.onItemDeseleccionado(item)
        }
    }

    private inner class ListaSeleccionableObservable : ListaObservable<T>() {
        override fun recargarLista() {
            removeAllSeleccion()
        }

        override fun onClickElemento(viewHolder: AbstractViewHolder<T>): Boolean {
            val item = viewHolder.item
            if (item != null) {
                val haySeleccionados = haySeleccionados()
                if (tipoSeleccion !== ETipoSeleccion.NONE) {
                    if (!haySeleccionados) {
                        if (tipoSeleccion !== ETipoSeleccion.LONGCLICK) {
                            addSeleccion(item)
                            return true
                        }
                    } else {
                        if (isSeleccionado(item)) {
                            removeSeleccion(item)
                        } else {
                            if (tipoSeleccion === ETipoSeleccion.CLICK_UNICO) {
                                val itemsSeleccionados = itemsSeleccionados
                                val oldSeleccionado = if (itemsSeleccionados.isNotEmpty()) itemsSeleccionados[0] else null
                                removeAllSeleccionSinEventos()
                                oldSeleccionado?.let { onItemDeseleccionado(it) }
                                addSeleccion(item)
                            } else {
                                addSeleccion(item)
                            }
                        }
                        return true
                    }
                }
            }
            return false
        }

        override fun onLongClickElemento(viewHolder: AbstractViewHolder<T>): Boolean {
            val item = viewHolder.item
            if (item != null) {
                if (tipoSeleccion !== ETipoSeleccion.NONE) {
                    if (!haySeleccionados()) {
                        addSeleccion(item)
                    } else {
                        if (isSeleccionado(item)) {
                            removeSeleccion(item)
                        } else {
                            if (tipoSeleccion === ETipoSeleccion.CLICK_UNICO) {
                                removeAllSeleccionSinEventos()
                                addSeleccion(item)
                            } else {
                                addSeleccion(item)
                            }
                        }
                    }
                    return true
                }
            }
            return false
        }

        override fun setItem(item: T) {
            val itemsSeleccionados = itemsSeleccionados
            var old: T? = null
            var i = 0
            val itemId = listaAdapter.getItemId(item)
            while (i < itemsSeleccionados.size && old == null) {
                if (itemId == listaAdapter.getItemId(itemsSeleccionados[i])) {
                    old = itemsSeleccionados[i]
                }
                i++
            }
            if (old != null) {
                removeSeleccion(old)
                addSeleccion(item)
            }
        }

        override fun deleteItem(item: T) {
            if (isSeleccionado(item)) {
                removeSeleccion(item)
            }
        }

        override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
            val item = listaAdapter.getItem(position)
            val seleccionado = isSeleccionado(item)
            holder.seleccionado = seleccionado
        }
    }

}