package com.victorlh.android.framework.core.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.victorlh.android.framework.core.fragment.AbstractFragment

/**
 * @author Victor
 * 09/06/2018
 */
abstract class AbstractDialogFragment : DialogFragment(), IDialog {

	private var viewId = 0
	protected lateinit var fragment: AbstractFragment
	private lateinit var mFragmentManager: FragmentManager

	protected var viewContainer: View? = null
	private var onCancelListener: ((dialog: IDialog) -> Unit)? = null
	private var onDissmissListener: (() -> Unit)? = null

	private var height: Int = ViewGroup.LayoutParams.MATCH_PARENT
	private var width: Int = ViewGroup.LayoutParams.MATCH_PARENT

	fun init(viewId: Int, fragment: AbstractFragment, fragmentManager: FragmentManager) {
		this.viewId = viewId
		this.fragment = fragment
		this.mFragmentManager = fragmentManager
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putInt("viewId", viewId)
	}

	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		if (savedInstanceState != null) {
			viewId = savedInstanceState.getInt("viewId")
		}
	}

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {
		if (viewId == 0) {
			return null
		}
		return inflater.inflate(viewId, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val childFragmentManager = childFragmentManager
		val fragmentTransaction = childFragmentManager.beginTransaction()
		fragmentTransaction.add(idLyFragmentBody, fragment)
		fragmentTransaction.commit()
	}

	override fun onStart() {
		super.onStart()
		val dialog = dialog
		if (dialog != null) {
			val window = dialog.window
			window?.setLayout(width, height)
			if (onCancelListener != null) {
				dialog.setOnCancelListener(DialogInterface.OnCancelListener {
					onCancelListener?.invoke(this@AbstractDialogFragment)
				})
				dialog.setOnDismissListener(DialogInterface.OnDismissListener {
					onDissmissListener?.invoke()
				})
			}
		}
	}

	protected abstract val idLyFragmentBody: Int

	override fun show() {
		val tag = fragment.javaClass.name
		show(mFragmentManager, tag)
	}

	override fun close() {
		dismiss()
	}

	override fun setOnCancelListener(evento: (dialog: IDialog) -> Unit) {
		this.onCancelListener = evento
	}

	override fun setOnDissmissListener(evento: () -> Unit) {
		this.onDissmissListener = evento
	}

	fun setHeight(height: Int) {
		this.height = height
	}

	fun setWidth(width: Int) {
		this.width = width
	}
}