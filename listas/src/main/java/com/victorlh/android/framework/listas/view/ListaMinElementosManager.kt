package com.victorlh.android.framework.listas.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victorlh.android.framework.listas.adapter.AbstractViewHolder
import kotlin.math.max

internal class ListaMinElementosManager(private val numeroMinimoItems: Int) {

	fun <T : RecyclerView.ViewHolder> wrapAdapter(listaAdapter: RecyclerView.Adapter<T>): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
		val wrapper = ListaMinElementosWrapper(listaAdapter)
		val observer = AdapterDataObserver(wrapper)
		listaAdapter.registerAdapterDataObserver(observer)
		return wrapper
	}

	private inner class ListaMinElementosWrapper<T : RecyclerView.ViewHolder>(private val listaAdapter: RecyclerView.Adapter<T>) : RecyclerView.Adapter<T>() {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
			return listaAdapter.onCreateViewHolder(parent, viewType)
		}

		override fun onBindViewHolder(holder: T, position: Int) {
			if (position >= listaAdapter.itemCount) {
				if (holder is AbstractViewHolder<*>) {
					holder.procesarEmpty()
				}
			} else {
				listaAdapter.onBindViewHolder(holder, position)
			}
		}

		override fun getItemCount(): Int {
			val itemCount = itemCountData
			return max(itemCount, numeroMinimoItems)
		}

		val itemCountData: Int
			get() = listaAdapter.itemCount

		val itemCountVoid: Int
			get() {
				val itemCountData = itemCountData
				return if (itemCountData >= numeroMinimoItems) 0 else numeroMinimoItems - itemCountData
			}
	}

	private inner class AdapterDataObserver<T : RecyclerView.ViewHolder>(private val adapter: ListaMinElementosWrapper<T>) : RecyclerView.AdapterDataObserver() {
		override fun onChanged() {
			adapter.notifyDataSetChanged()
		}

		override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
			adapter.notifyItemRangeChanged(positionStart, itemCount)
		}

		override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
			adapter.notifyItemRangeRemoved(adapter.itemCount - 1, 1)
			adapter.notifyItemRangeInserted(positionStart, itemCount)
		}

		override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
			adapter.notifyItemRangeRemoved(positionStart, itemCount)
		}

		override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
			adapter.notifyItemMoved(fromPosition, toPosition)
		}

	}

}