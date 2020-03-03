package com.victorlh.android.framework.listas.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator
import com.victorlh.android.framework.listas.R
import com.victorlh.android.framework.listas.adapter.AbstractLista
import com.victorlh.android.framework.listas.adapter.Lista.OnDataListChangeListener
import com.victorlh.android.framework.listas.view.shimmer.ShimmerListView

/**
 * @author Victor
 * 26/12/2018
 */
open class ListaView : LinearLayout {

	lateinit var listaView: RecyclerView
		private set

	private lateinit var listaShimmer: ShimmerListView
	private lateinit var lyListaVacia: ViewGroup
	private lateinit var ivEmpty: ImageView
	private lateinit var tvEmpty: TextView

	var adapter: RecyclerView.Adapter<*>? = null
		private set

	private var layoutManager: RecyclerView.LayoutManager? = null
	private var hasShimmer = false
	private var voidItemCount = 0

	constructor(context: Context) : super(context) {
		init(context, null)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init(context, attrs)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		init(context, attrs)
	}

	private fun init(context: Context, attrs: AttributeSet?) {
		val view = View.inflate(context, R.layout.view_listas, this)
		listaView = view.findViewById(R.id.listaRFList)
		listaShimmer = view.findViewById(R.id.listaRFShimmer)
		lyListaVacia = view.findViewById(R.id.listaRfLyVacia)
		ivEmpty = view.findViewById(R.id.listaRFImagenVacia)
		tvEmpty = view.findViewById(R.id.listaRFTextoVacia)

		listaView.isNestedScrollingEnabled = false
		if (attrs != null) {
			val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListaView)
			val textoListaVacia = typedArray.getString(R.styleable.ListaView_textoListaVacia)
			val imagenListaVacia = typedArray.getDrawable(R.styleable.ListaView_imagenListaVacia)
			voidItemCount = typedArray.getInteger(R.styleable.ListaView_voidItemCount, 0)
			hasShimmer = typedArray.getBoolean(R.styleable.ListaView_hasShimmer, false)
			val numShimmer = typedArray.getInteger(R.styleable.ListaView_numShimmers, 8)
			val layoutShimmerId = typedArray.getResourceId(R.styleable.ListaView_layoutShimmer, 0)
			typedArray.recycle()

			textoListaVacia?.let { setTextoEmpty(it) }
			imagenListaVacia?.let { setImagenEmpty(it) }

			setShimmerCount(numShimmer)
			setLayoutShimmerId(layoutShimmerId)
		}
	}

	fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
		val context = context
		val layoutManager = LinearLayoutManager(context)
		setAdapter(adapter, layoutManager)
	}

	fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) {
		setAdapter(adapter, layoutManager, null)
	}

	fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager, animator: ItemAnimator?) {
		var adapterAux = adapter
		var animatorAux = animator

		this.adapter = adapterAux
		this.layoutManager = layoutManager
		if (adapterAux is AbstractLista<*>) {
			adapterAux.setOnDataListChangeListener(ListaOnDataChangeAdapter())
		}
		if (animatorAux == null) {
			animatorAux = RefactoredDefaultItemAnimator()
		}
		if (voidItemCount > 0) {
			val listaMinElementosManager = ListaMinElementosManager(voidItemCount)
			adapterAux = listaMinElementosManager.wrapAdapter(adapterAux)
		}
		listaView.layoutManager = layoutManager
		listaView.adapter = adapterAux
		listaView.itemAnimator = animatorAux
		listaView.setHasFixedSize(false)
		loadListaEmpty()
	}

	private fun loadListaEmpty() {
		if (isEmpty) {
			listaView.visibility = View.GONE
			listaShimmer.visibility = View.GONE
			lyListaVacia.visibility = View.VISIBLE
		} else {
			listaShimmer.visibility = View.GONE
			lyListaVacia.visibility = View.GONE
			listaView.layoutManager = layoutManager
			listaView.visibility = View.VISIBLE
		}
	}

	fun startShimmer() {
		stopShimmer()
		if (hasShimmer) {
			lyListaVacia.visibility = View.GONE
			listaView.visibility = View.GONE
			listaShimmer.visibility = View.VISIBLE
			listaView.layoutManager = null
			listaShimmer.setLayoutManager(layoutManager)
			listaShimmer.startShimmer()
		}
	}

	fun stopShimmer() {
		listaShimmer.stopShimmer()
		listaShimmer.visibility = View.GONE
		listaShimmer.setLayoutManager(null)
		loadListaEmpty()
	}

	protected val isEmpty: Boolean
		get() {
			val adapter = listaView.adapter
			return adapter == null || adapter.itemCount <= 0
		}

	fun setTextoEmpty(textoEmpty: String) {
		tvEmpty.text = textoEmpty
	}

	fun setTextoEmpty(@StringRes textoEmpty: Int) {
		tvEmpty.setText(textoEmpty)
	}

	fun setImagenEmpty(@DrawableRes imagenEmpty: Int) {
		ivEmpty.setImageResource(imagenEmpty)
	}

	fun setImagenEmpty(imagenEmpty: Drawable) {
		ivEmpty.setImageDrawable(imagenEmpty)
	}

	fun setVoidItemCount(voidItemCount: Int) {
		this.voidItemCount = voidItemCount
	}

	fun setHasShimmer(hasShimmer: Boolean) {
		this.hasShimmer = hasShimmer
	}

	fun setShimmerCount(shimmerCount: Int) {
		listaShimmer.setItemCount(shimmerCount)
	}

	fun setLayoutShimmerId(@LayoutRes layoutShimmerId: Int) {
		listaShimmer.setLyShimmer(layoutShimmerId)
	}

	fun addOnScrollListener(listener: RecyclerView.OnScrollListener) {
		listaView.addOnScrollListener(listener)
	}

	private inner class ListaOnDataChangeAdapter : OnDataListChangeListener {
		override fun onSetData() {
			stopShimmer()
		}

		override fun onVoidData() {
			stopShimmer()
		}

		override fun onChangeData() {
			stopShimmer()
		}
	}
}