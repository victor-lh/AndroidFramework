package com.victorlh.android.framework.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.victorlh.android.framework.core.flujo.ITransitionStackAnimation

abstract class AbstractFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

	@CallSuper
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	@CallSuper
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		init(savedInstanceState)
	}

	@CallSuper
	protected open fun init(savedInstanceState: Bundle?) {
	}

	fun <T : View?> findViewById(@IdRes id: Int): T {
		require(!(view == null || id == View.NO_ID)) { "ID does not reference a View inside this View" }
		return view!!.findViewById<T>(id)
				?: throw IllegalArgumentException("ID does not reference a View inside this View")
	}

	fun setFragment(layout: Int, fragment: AbstractFragment, tag: String) {
		setFragment(layout, fragment, tag, null)
	}

	fun setFragment(layout: Int, fragment: AbstractFragment, tag: String, animation: ITransitionStackAnimation?) {
		setFragment(layout, fragment, tag, animation?.xmlEntrada ?: 0, animation?.xmlSalida ?: 0)
	}

	fun setFragment(layout: Int, fragment: AbstractFragment, tag: String, @AnimRes xmlEntrada: Int, @AnimRes xmlSalida: Int) {
		val fragmentManager = childFragmentManager
		val fragmentTransaction = fragmentManager.beginTransaction()
		if (xmlEntrada != 0 && xmlSalida != 0) {
			fragmentTransaction.setCustomAnimations(xmlEntrada, xmlSalida)
		}
		fragmentTransaction.replace(layout, fragment, tag)
		fragmentTransaction.commit()
	}
}