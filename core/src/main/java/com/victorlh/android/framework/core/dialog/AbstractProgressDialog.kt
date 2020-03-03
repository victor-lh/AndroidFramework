package com.victorlh.android.framework.core.dialog

import android.content.Context
import android.widget.ProgressBar

/**
 * @author Victor
 * 09/06/2018
 */
abstract class AbstractProgressDialog(context: Context) : AbstractDialog(context), IProgressDialog {

	init {
		cancelable = false
	}

	override var progresoMaximo = 0
	protected abstract val progressBar: ProgressBar?
	override var progreso: Int = 0
		get() = field
		set(value) {
			progressBar?.progress = value
		}


}