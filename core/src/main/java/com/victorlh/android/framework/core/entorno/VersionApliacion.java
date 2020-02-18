package com.victorlh.android.framework.core.entorno;

import androidx.annotation.NonNull;

import com.victorlh.tools.versionado.Version;

/**
 * Created by victor on 19/03/2017.
 */
public final class VersionApliacion {

	private Version versionActual;
	private Version ultimaVersion;
	private Version versionMinima;
	private Version versionBeta;

	public VersionApliacion(Version versionActual) {
		this.versionActual = versionActual;
	}

	public Version getVersionActual() {
		return versionActual;
	}

	public Version getUltimaVersion() {
		return ultimaVersion == null ? Version.getVersionNull() : ultimaVersion;
	}

	public Version getVersionMinima() {
		return versionMinima == null ? Version.getVersionNull() : versionMinima;
	}

	public void setVersionMinima(Version versionMinima) {
		this.versionMinima = versionMinima;
	}

	public void setUltimaVersion(Version ultimaVersion) {
		this.ultimaVersion = ultimaVersion;
	}

	public void setVersionBeta(Version versionBeta) {
		this.versionBeta = versionBeta;
	}

	public boolean isActualizado() {
		return !versionActual.isMenor(getUltimaVersion());
	}

	public boolean isActualizacionObligatoria() {
		return !isActualizado() && versionActual.isMenor(getVersionMinima());
	}

	@NonNull
	@Override
	public String toString() {
		return versionActual.toString();
	}

	public boolean isVersionBeta() {
		return versionActual.equals(versionBeta);
	}
}
