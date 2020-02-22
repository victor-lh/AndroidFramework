package com.victorlh.android.framework.listas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Victor
 * 17/12/2018
 */
abstract class AbstractViewHolder<T> : RecyclerView.ViewHolder {

    private var layoutItem: Int? = null
    private var layoutItemVoid: Int? = null
    private var viewElemento: View? = null
    private var viewVacio: View? = null

    private constructor(itemView: View) : super(itemView)

    constructor(itemView: View, @LayoutRes layoutItem: Int, @LayoutRes layoutItemVoid: Int? = null) : this(itemView) {
        this.layoutItem = layoutItem
        this.layoutItemVoid = layoutItemVoid
    }

    constructor(itemView: View, viewElemento: View, viewVacio: View? = null) : this(itemView) {
        this.viewElemento = viewElemento
        this.viewVacio = viewVacio
    }

    lateinit var lista: AbstractLista<T>

    var item: T? = null
        private set

    var seleccionado = false
        internal set(value) {
            field = value
            if (seleccionado) {
                onSelect()
            } else {
                onUnselect()
            }
        }

    fun procesar(item: T) {
        this.item = item
        if (itemView is ViewGroup) {
            onViewsOldRemove()
            itemView.removeAllViews()

            val view: View = if (viewElemento != null) viewElemento!! else inflateView(layoutItem)

            val layoutParams = view.layoutParams
            itemView.setLayoutParams(layoutParams)
            itemView.addView(view, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            if (onClickListaAdapter != null) {
                view.setOnClickListener(onClickListaAdapter)
                view.setOnLongClickListener(onClickListaAdapter)
            }
        }
        onProcesar()
    }

    protected fun onViewsOldRemove() {}

    fun procesarEmpty() {
        item = null
        if (itemView is ViewGroup) {
            itemView.removeAllViews()

            val view: View = if (viewVacio != null) viewVacio!! else inflateView(layoutItemVoid)

            val layoutParams = view.layoutParams
            itemView.setLayoutParams(layoutParams)
            itemView.addView(view, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }

    protected abstract fun onProcesar()

    open fun onSelect() {}
    open fun onUnselect() {}

    protected fun notifyItemChange() {
        if (posicion >= 0) {
            lista.notifyItemChanged(posicion)
        }
    }

    protected val posicion: Int
        get() = if (item != null) lista.getItemPosition(item!!) else -1

    private var onClickListaAdapter: AbstractLista<T>.OnClickListaAdapter? = null

    internal fun setOnClickListaAdapter(onClickListaAdapter: AbstractLista<T>.OnClickListaAdapter) {
        this.onClickListaAdapter = onClickListaAdapter
    }

    private fun inflateView(layout: Int?): View {
        if (layout == null || itemView !is ViewGroup) {
            return View(itemView.context)
        }
        val inflater = LayoutInflater.from(itemView.context)
        return inflater.inflate(layout, itemView, false)
    }

}