package com.victorlh.android.framework.core.logs;

public enum NivelLog {
	OFF(Integer.MAX_VALUE),
	ERROR(1000),
	WARN(800),
	INFO(600),
	DEBUG(500),
	TRACE(400),
	ALL(Integer.MIN_VALUE);

	public final int nivel;

	NivelLog(int nivel) {
		this.nivel = nivel;
	}

	public static NivelLog getInstance(String name) {
		try {
			return valueOf(name);
		} catch (IllegalArgumentException e) {
			return ERROR;
		}
	}
}