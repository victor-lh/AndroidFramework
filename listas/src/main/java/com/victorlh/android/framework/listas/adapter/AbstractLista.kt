package com.victorlh.android.framework.listas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.victorlh.android.framework.listas.ItemLista
import com.victorlh.android.framework.listas.R
import com.victorlh.android.framework.listas.adapter.Lista.*
import com.victorlh.tools.ordenacion.ElementoOrdenado
import com.victorlh.tools.ordenacion.KeyOrdenacion
import com.victorlh.tools.ordenacion.UtilOrdenacion

/**
 * Created by victor on 07/07/2017.
 */
abstract class AbstractLista<T> protected constructor(val clazz: Class<T>) : RecyclerView.Adapter<AbstractViewHolder<T>>(), Lista<T> {

    private var lista: MutableList<T> = ArrayList()
    private val viewHolders: HashMap<T, AbstractViewHolder<T>> = HashMap()
    private val observableAdapter: ListaObservableAdapter<T> = ListaObservableAdapter()

    private var onClickElementoListener: OnClickElementoListener<T>? = null
    private var onLongClickElementoListener: OnLongClickElementoListener<T>? = null
    private var onDataListChangeListener: OnDataListChangeListener? = null
    private var keyOrdenacion: KeyOrdenacion? = null

    fun setKeyOrdenacion(keyOrdenacion: KeyOrdenacion?) {
        this.keyOrdenacion = keyOrdenacion
    }

    protected abstract fun createViewHolder(view: View?): AbstractViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T> {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_listas_wrapper, parent, false)
        val viewHolder = createViewHolder(itemView)
        viewHolder.lista = this
        itemView.tag = viewHolder
        return viewHolder
    }

    @CallSuper
    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
        val listener = OnClickListaAdapter(holder)
        holder.setOnClickListaAdapter(listener)

        val item = getItem(position)
        viewHolders[item] = holder
        holder.procesar(item)
        observableAdapter.onBindViewHolder(holder, position)
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position) ?: return super.getItemId(position)
        return getItemId(item)
    }

    override fun getLista(): MutableList<T> {
        return ArrayList(lista)
    }

    override fun getItem(position: Int): T {
        return lista[position]
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun getItemPosition(item: T): Int {
        return getItemPosition(item, false)
    }

    override fun getItemPosition(item: T, byId: Boolean): Int {
        var i = lista.indexOf(item)
        if (i == -1 && byId) {
            val id = getItemId(item)
            val aux: T? = getItemById(id)
            if (aux != null) {
                i = lista.indexOf(aux)
            }
        }
        return i
    }

    override fun getItemById(id: Long): T? {
        for (item in lista) {
            if (getItemId(item) == id) {
                return item
            }
        }
        return null
    }

    override fun setLista(lista: Array<T>) {
        val ts = listOf(*lista)
        setLista(ts)
    }

    @Suppress("UNCHECKED_CAST")
    override fun setLista(lista: List<T>) {
        if (keyOrdenacion != null && ElementoOrdenado::class.java.isAssignableFrom(clazz)) {
            UtilOrdenacion.ordenar(lista as List<ElementoOrdenado>, keyOrdenacion)
        }
        val empty = this.lista.isEmpty()
        this.lista.clear()
        this.lista.addAll(lista)
        recargarLista()
        if (onDataListChangeListener != null) {
            if (empty && this.lista.isNotEmpty()) {
                onDataListChangeListener!!.onSetData()
            } else if (!empty && this.lista.isEmpty()) {
                onDataListChangeListener!!.onVoidData()
            } else {
                onDataListChangeListener!!.onChangeData()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun addItem(item: T) {
        if (keyOrdenacion != null && ElementoOrdenado::class.java.isAssignableFrom(clazz)) {
            val aux = getLista()
            aux.add(item)
            UtilOrdenacion.ordenar(aux as List<ElementoOrdenado>, keyOrdenacion)
            val position = aux.indexOf(item)
            addItem(item, position)
        } else {
            addItem(item, lista.size)
        }
    }

    override fun addItem(item: T, position: Int) {
        val empty = lista.isEmpty()
        if (position == lista.size) {
            lista.add(item)
        } else {
            lista.add(position, item)
        }
        notifyItemInserted(position)
        if (onDataListChangeListener != null) {
            if (empty) {
                onDataListChangeListener!!.onSetData()
            } else {
                onDataListChangeListener!!.onChangeData()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun setItem(item: T) {
        val itemPosition = getItemPosition(item, true)
        if (itemPosition == -1) {
            addItem(item)
        } else {
            lista[itemPosition] = item
            notifyItemChanged(itemPosition)
            if (keyOrdenacion != null && ElementoOrdenado::class.java.isAssignableFrom(clazz)) {
                val auxList = getLista()
                UtilOrdenacion.ordenar(auxList as List<ElementoOrdenado?>, keyOrdenacion)
                val destino = auxList.indexOf(item)
                if (itemPosition != destino) {
                    lista = auxList
                    notifyItemMoved(itemPosition, destino)
                }
            }
            observableAdapter.setItem(item)
            if (onDataListChangeListener != null) {
                onDataListChangeListener!!.onChangeData()
            }
        }
    }

    override fun deleteItem(item: T) {
        val itemPosition = getItemPosition(item, true)
        if (itemPosition != -1) {
            lista.removeAt(itemPosition)
            notifyItemRemoved(itemPosition)
            observableAdapter.deleteItem(item)
            if (onDataListChangeListener != null && lista.isEmpty()) {
                onDataListChangeListener!!.onVoidData()
            }
        }
    }

    override fun recargarLista() {
        viewHolders.clear()
        notifyDataSetChanged()
        observableAdapter.recargarLista()
    }

    override fun setOnClickElementoListener(onClickElementoListener: OnClickElementoListener<T>) {
        this.onClickElementoListener = onClickElementoListener
    }

    override fun setOnLongClickElementoListener(onLongClickElementoListener: OnLongClickElementoListener<T>) {
        this.onLongClickElementoListener = onLongClickElementoListener
    }

    override fun setOnDataListChangeListener(onDataListChangeListener: OnDataListChangeListener) {
        this.onDataListChangeListener = onDataListChangeListener
    }

    fun getViewHolder(item: T): AbstractViewHolder<T>? {
        return viewHolders[item]
    }

    fun registrarObservable(observable: ListaObservable<T>?) {
        observableAdapter.registrarObservable(observable!!)
    }

    fun removerObservable(observable: ListaObservable<T>?) {
        observableAdapter.removerObservable(observable)
    }

    internal fun getItemId(item: T): Long {
        return if (item is ItemLista) (item as ItemLista).id else item.hashCode().toLong()
    }

    internal inner class OnClickListaAdapter constructor(private val holder: AbstractViewHolder<T>) : View.OnClickListener, OnLongClickListener {
        override fun onClick(v: View) {
            val interceptado = observableAdapter.onClickElemento(holder)
            if (!interceptado && onClickElementoListener != null) {
                onClickElementoListener!!.onClickElemento(holder)
            }
        }

        override fun onLongClick(v: View): Boolean {
            val interceptObservable = observableAdapter.onLongClickElemento(holder)
            return if (!interceptObservable && onLongClickElementoListener != null) {
                onLongClickElementoListener!!.onLongClickElemento(holder)
            } else interceptObservable
        }

    }
}