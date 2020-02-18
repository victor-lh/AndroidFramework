package com.victorlh.android.framework.core.dialog;

/**
 * Created by victor on 28/05/2017.
 */

public interface IDialog {

	void show();

	void close();

	void setOnCancelListener(EventosDialogs.OnCancelListener evento);

	void setOnDissmissListener(EventosDialogs.OnDissmissListener evento);
}
