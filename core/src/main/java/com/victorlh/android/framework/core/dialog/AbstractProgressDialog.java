package com.victorlh.android.framework.core.dialog;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

/**
 * @author Victor
 * 09/06/2018
 */
public abstract class AbstractProgressDialog extends AbstractDialog implements IProgressDialog {

	protected int progresoMaximo;

	public AbstractProgressDialog(Context context) {
		super(context);
	}

	@Nullable
	protected abstract ProgressBar getProgressBar();

	@Override
	protected void initValues() {
		super.initValues();
		setCancelable(false);
	}

	@Override
	public void setProgreso(int progreso) {
		ProgressBar progressBar = getProgressBar();
		if (progressBar != null) {
			progressBar.setProgress(progreso);
		}
	}

	@Override
	public void setProgresoMaximo(int progresoMaximo) {
		this.progresoMaximo = progresoMaximo;
	}

}
