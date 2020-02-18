package com.victorlh.android.framework.logger

import android.util.Log

class AndroidLogObserver : LoggerObserver {

    var nivelLog: NivelLog = NivelLogImpl.ALL

    override fun log(trazaLog: TrazaLog) {
        if (trazaLog.nivel.nivelLog >= nivelLog.nivelLog) {
            imprimir(trazaLog)
        }
    }

    private fun imprimir(trazaLog: TrazaLog) {
        when {
            trazaLog.nivel.nivelLog <= NivelLogImpl.TRACE.nivelLog ->
                when {
                    trazaLog.error != null -> Log.v(trazaLog.tag, trazaLog.mensaje, trazaLog.error)
                    else -> Log.v(trazaLog.tag, trazaLog.mensaje)
                }
            trazaLog.nivel.nivelLog <= NivelLogImpl.DEBUG.nivelLog ->
                when {
                    trazaLog.error != null -> Log.d(trazaLog.tag, trazaLog.mensaje, trazaLog.error)
                    else -> Log.d(trazaLog.tag, trazaLog.mensaje)
                }
            trazaLog.nivel.nivelLog <= NivelLogImpl.INFO.nivelLog ->
                when {
                    trazaLog.error != null -> Log.i(trazaLog.tag, trazaLog.mensaje, trazaLog.error)
                    else -> Log.i(trazaLog.tag, trazaLog.mensaje)
                }
            trazaLog.nivel.nivelLog <= NivelLogImpl.WARN.nivelLog ->
                when {
                    trazaLog.error != null -> Log.w(trazaLog.tag, trazaLog.mensaje, trazaLog.error)
                    else -> Log.w(trazaLog.tag, trazaLog.mensaje)
                }
            trazaLog.nivel.nivelLog <= NivelLogImpl.ERROR.nivelLog ->
                when {
                    trazaLog.error != null -> Log.e(trazaLog.tag, trazaLog.mensaje, trazaLog.error)
                    else -> Log.e(trazaLog.tag, trazaLog.mensaje)
                }
        }
    }
}