package com.victorlh.android.framework.core.errores;

public class ErrorGeneral extends Exception {

	private IAvisoError[] avisos;

	public ErrorGeneral() {
	}

	public ErrorGeneral(String message) {
		super(message);
	}

	public ErrorGeneral(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorGeneral(Throwable cause) {
		super(cause);
	}

	protected void setAvisos(IAvisoError[] avisos) {
		this.avisos = avisos;
	}

	public IAvisoError[] getAvisos() {
		return avisos == null ? new IAvisoError[0] : avisos;
	}
}
