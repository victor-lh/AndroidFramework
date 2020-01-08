package com.victorlh.android.framework.core.dialog;

/**
 * @author Victor
 * 15/12/2018
 */
public interface EventosDialogs {

	interface OnCancelListener {
		void onCancel(IDialog dialog);
	}

	interface OnDissmissListener {
		void onDissmiss();
	}

}
