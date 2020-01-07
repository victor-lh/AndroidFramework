package com.victorlh.android.framework.core.aplicacion;

import android.app.Application;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.core.logs.LogApp;

public class AplicacionController {

	private static volatile AplicacionController instance;

	private boolean inicializado = false;

	private AppProperties appProperties;

	private LogApp logApp;

	private AplicacionController() {
	}

	public static AplicacionController getInstance() {
		if (instance == null) {
			synchronized (AplicacionController.class) {
				if (instance == null) {
					instance = new AplicacionController();
				}
			}
		}
		return instance;
	}

	public static void inicializar(Application application) {
		AplicacionController instance = getInstance();
		if (!instance.inicializado) {
			instance.init(application);
		}
	}

	private synchronized void init(Application application) {
		if (!inicializado) {
			this.appProperties = new AppProperties(application);
			this.logApp = new LogApp(application);
			this.inicializado = true;
		}
	}

	@NonNull
	public AppProperties getAppProperties() {
		checkInicializado();
		return appProperties;
	}

	@NonNull
	public LogApp getLogApp() {
		checkInicializado();
		return logApp;
	}

	private void checkInicializado() {
		if (!inicializado) {
			throw new IllegalStateException("No se ha inicializado el AplicacionController");
		}
	}
}
