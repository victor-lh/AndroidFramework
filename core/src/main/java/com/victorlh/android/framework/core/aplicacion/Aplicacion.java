package com.victorlh.android.framework.core.aplicacion;

import android.app.Application;

import androidx.annotation.CallSuper;

public class Aplicacion extends Application {

	@Override
	@CallSuper
	public void onCreate() {
		super.onCreate();
		AplicacionController.inicializar(this);
	}
}
