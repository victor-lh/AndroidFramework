package com.victorlh.android.framework.core.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author Victor
 * 09/06/2018
 */
public abstract class AbstractDialog implements IDialog, DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

	protected final Context context;

	private boolean cancelable;
	private EventosDialogs.OnCancelListener onCancelListener;
	private EventosDialogs.OnDissmissListener onDissmissListener;

	private Dialog dialog;

	public AbstractDialog(Context context) {
		this.context = context;
		initValues();
	}

	protected void initValues() {
		this.cancelable = true;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	@Override
	public void setOnCancelListener(EventosDialogs.OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	@Override
	public void setOnDissmissListener(EventosDialogs.OnDissmissListener onDissmissListener) {
		this.onDissmissListener = onDissmissListener;
	}

	@Override
	public void show() {
		boolean show = true;
		if (context instanceof Activity) {
			show = !((Activity) context).isFinishing();
		}
		if (show) {
			this.dialog = new Dialog(context);
			this.dialog.setCancelable(cancelable);
			this.dialog.setOnCancelListener(this);
			this.dialog.setOnDismissListener(this);
			onShow(dialog);
			dialog.show();
		}
	}

	@Override
	public void close() {
		if (dialog != null && dialog.isShowing()) {
			Activity ownerActivity = dialog.getOwnerActivity();
			if (ownerActivity == null || !ownerActivity.isDestroyed() || !ownerActivity.isFinishing()) {
				try {
					dialog.dismiss();
				} catch (IllegalStateException ignored) {
				}
			}
		}
	}

	protected abstract void onShow(Dialog dialog);

	@Override
	public void onCancel(DialogInterface dialog) {
		if (onCancelListener != null) {
			onCancelListener.onCancel(this);
		}
	}

	@Override
	public void onDismiss(DialogInterface dialogInterface) {
		if (onDissmissListener != null) {
			onDissmissListener.onDissmiss();
		}
	}
}
