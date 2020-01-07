package com.victorlh.android.framework.core.logs;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TrazaLog {

	private NivelLog nivel;
	private String tag;
	private String mensaje;
	private Throwable error;

	TrazaLog(NivelLog nivel, String tag, String mensaje, Throwable e) {
		this.nivel = nivel;
		this.tag = tag;
		this.mensaje = mensaje;
		this.error = e;
	}

	public NivelLog getNivel() {
		return nivel;
	}

	public String getTag() {
		return tag;
	}

	public String getMensaje() {
		return mensaje;
	}

	public Throwable getError() {
		return error;
	}

	public String getStringError() {
		return error == null ? "" : TrazaLog.getStringError(error);
	}

	void imprimir() {
		TrazaLog.imprimir(nivel, tag, mensaje, error);
	}

	private static void imprimir(NivelLog nivel, String tag, String mensaje, Throwable e) {
		if (mensaje == null) {
			mensaje = "";
		}
		switch (nivel) {
			case TRACE:
			case DEBUG:
				Log.d(tag, mensaje);
				break;
			case INFO:
				if (e != null) {
					Log.i(tag, mensaje, e);
				} else {
					Log.i(tag, mensaje);
				}
				break;
			case WARN:
				if (e != null) {
					Log.w(tag, mensaje, e);
				} else {
					Log.w(tag, mensaje);
				}
				break;
			case ERROR:
				if (e != null) {
					Log.e(tag, mensaje, e);
				} else {
					Log.e(tag, mensaje);
				}
				break;
		}
	}

	private static String getStringError(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
