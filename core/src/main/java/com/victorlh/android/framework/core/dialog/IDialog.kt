package com.victorlh.android.framework.core.dialog

/**
 * Created by victor on 28/05/2017.
 */
interface IDialog {
    fun show()
    fun close()
    fun setOnCancelListener(evento: (dialog: IDialog) -> Unit)
    fun setOnDissmissListener(evento: () -> Unit)
}