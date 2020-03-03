package com.victorlh.android.framework.listas.view.shimmer

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerListView : ShimmerFrameLayout {

	companion object {
		private const val DEFAULT_ITEM_COUNT = 8
	}

	private var lista: RecyclerView? = null
	private var itemCountShimmer = DEFAULT_ITEM_COUNT
	private var lyShimmer = -1
	private var layoutManager: RecyclerView.LayoutManager? = null

	constructor(context: Context) : super(context) {
		init()
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init()
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		init()
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
		init()
	}

	private fun init() {
		lista = RecyclerView(context)
		val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
		lista!!.layoutParams = layoutParams
		addView(lista)
		loadAdapter()
	}

	fun setItemCount(itemCount: Int) {
		this.itemCountShimmer = itemCount
		loadAdapter()
	}

	fun setLyShimmer(lyShimmer: Int) {
		this.lyShimmer = lyShimmer
		loadAdapter()
	}

	fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
		this.layoutManager = layoutManager
		loadAdapter()
	}

	private fun loadAdapter() {
		if (itemCountShimmer >= 0 || lyShimmer >= 0) {
			val adapter = ShimmerAdapter()
			var layoutManager = layoutManager
			if (layoutManager == null) {
				layoutManager = LinearLayoutManager(context)
			}
			lista!!.layoutManager = layoutManager
			lista!!.adapter = adapter
		}
	}

	private inner class ShimmerAdapter : RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder>() {

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
			val inflater = LayoutInflater.from(context)
			val view = inflater.inflate(lyShimmer, parent, false)
			return ShimmerViewHolder(view)
		}

		override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {}

		private inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

		override fun getItemCount(): Int = itemCountShimmer
	}
}