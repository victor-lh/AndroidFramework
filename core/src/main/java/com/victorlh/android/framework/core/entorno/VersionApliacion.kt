package com.victorlh.android.framework.core.entorno

import com.victorlh.tools.versionado.Version

/**
 * Created by victor on 19/03/2017.
 */
open class VersionApliacion(val versionActual: Version) {

    var ultimaVersion: Version? = null
        get() = if (field == null) Version.getVersionNull() else field
    var versionMinima: Version? = null
        get() = if (field == null) Version.getVersionNull() else field
    private var versionBeta: Version? = null

    fun setVersionBeta(versionBeta: Version?) {
        this.versionBeta = versionBeta
    }

    val isActualizado: Boolean
        get() = !versionActual.isMenor(ultimaVersion)

    val isActualizacionObligatoria: Boolean
        get() = !isActualizado && versionActual.isMenor(versionMinima)

    override fun toString(): String {
        return versionActual.toString()
    }

    fun isVersionBeta(): Boolean {
        return versionActual == versionBeta
    }

}