package com.victorlh.android.framework.core.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

/**
 * @author Victor
 * 09/06/2018
 */
abstract class AbstractDialog(protected val context: Context) : IDialog,
    DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    var cancelable = true
    private var onCancelListener: ((dialog: IDialog) -> Unit)? = null
    private var onDissmissListener: (() -> Unit)? = null
    private var dialog: Dialog? = null

    override fun setOnCancelListener(evento: (dialog: IDialog) -> Unit) {
        this.onCancelListener = evento
    }

    override fun setOnDissmissListener(evento: () -> Unit) {
        this.onDissmissListener = evento
    }

    override fun show() {
        var show = true
        if (context is Activity) {
            show = !context.isFinishing
        }
        if (show) {
            dialog = Dialog(context)
            dialog!!.setCancelable(cancelable)
            dialog!!.setOnCancelListener(this)
            dialog!!.setOnDismissListener(this)
            onShow(dialog!!)
            dialog!!.show()
        }
    }

    override fun close() {
        if (dialog != null && dialog!!.isShowing) {
            val ownerActivity = dialog!!.ownerActivity
            if (ownerActivity == null || !ownerActivity.isDestroyed || !ownerActivity.isFinishing) {
                try {
                    dialog!!.dismiss()
                } catch (ignored: Throwable) {
                }
            }
        }
    }

    protected abstract fun onShow(dialog: Dialog)

    override fun onCancel(dialog: DialogInterface) {
        onCancelListener?.invoke(this)
    }

    override fun onDismiss(dialogInterface: DialogInterface) {
        onDissmissListener?.invoke()
    }
}