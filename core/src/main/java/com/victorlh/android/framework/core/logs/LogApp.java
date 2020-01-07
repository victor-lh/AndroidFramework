package com.victorlh.android.framework.core.logs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.core.aplicacion.AplicacionController;

import java.util.ArrayList;
import java.util.List;

public class LogApp {

	protected final Context context;
	private List<LogAppObserver> observers = new ArrayList<>();
	private NivelLog nivelLog;

	public LogApp(Context context) {
		this.context = context;
	}

	@NonNull
	public static LogApp getInstance() {
		AplicacionController aplicacionController = AplicacionController.getInstance();
		return aplicacionController.getLogApp();
	}

	public void setNivelLog(NivelLog nivelLog) {
		this.nivelLog = nivelLog;
	}

	private void logInternal(NivelLog nivel, String tag, String mensaje, Throwable e) {
		if (nivel.nivel >= nivelLog.nivel) {
			TrazaLog traza = new TrazaLog(nivel, tag, mensaje, e);
			traza.imprimir();

			notifyObservers(traza);
		}
	}

	public static void log(NivelLog nivel, String tag, String mensaje, Throwable e) {
		LogApp.getInstance().logInternal(nivel, tag, mensaje, e);
	}

	public static void log(NivelLog nivel, String tag, String mensaje) {
		LogApp.log(nivel, tag, mensaje, null);
	}

	public static void info(String tag, String mensaje) {
		LogApp.info(tag, mensaje, null);
	}

	public static void info(String tag, String mensaje, Throwable e) {
		LogApp.log(NivelLog.INFO, tag, mensaje, e);
	}

	public static void warn(String tag, String mensaje) {
		LogApp.warn(tag, mensaje, null);
	}

	public static void warn(String tag, String mensaje, Throwable e) {
		LogApp.log(NivelLog.WARN, tag, mensaje, e);
	}

	public static void error(String tag, String mensaje) {
		LogApp.error(tag, mensaje, null);
	}

	public static void error(String tag, String mensaje, Throwable e) {
		LogApp.log(NivelLog.ERROR, tag, mensaje, e);
	}

	public static void debug(String tag, String mensaje) {
		LogApp.log(NivelLog.DEBUG, tag, mensaje, null);
	}

	public static void trace(String tag, String mensaje) {
		LogApp.log(NivelLog.TRACE, tag, mensaje, null);
	}

	public void addObserver(LogAppObserver o) {
		if (o == null)
			throw new NullPointerException();
		if (!observers.contains(o)) {
			observers.add(o);
		}
	}

	public void deleteObserver(LogAppObserver o) {
		observers.remove(o);
	}

	private void notifyObservers(TrazaLog trazaLog) {
		for (LogAppObserver observer : observers) {
			observer.log(trazaLog);
		}
	}
}
