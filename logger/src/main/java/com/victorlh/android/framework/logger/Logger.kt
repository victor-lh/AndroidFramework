package com.victorlh.android.framework.logger

import androidx.annotation.Size

object Logger {

    private val observers: ArrayList<LoggerObserver> = ArrayList()

    fun registrarObserver(observer: LoggerObserver) = observers.add(observer)
    fun suprimirObserver(observer: LoggerObserver) = observers.remove(observer)

    fun trace(@Size(max = 23) tag: String, mensaje: String) = trace(tag, mensaje, null)
    fun trace(@Size(max = 23) tag: String, e: Throwable) = log(NivelLogImpl.TRACE, tag, e)
    fun trace(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = log(NivelLogImpl.TRACE, tag, mensaje, e)

    fun debug(@Size(max = 23) tag: String, mensaje: String) = debug(tag, mensaje, null)
    fun debug(@Size(max = 23) tag: String, e: Throwable) = log(NivelLogImpl.DEBUG, tag, e)
    fun debug(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = log(NivelLogImpl.DEBUG, tag, mensaje, e)

    fun info(@Size(max = 23) tag: String, mensaje: String) = info(tag, mensaje, null)
    fun info(@Size(max = 23) tag: String, e: Throwable) = log(NivelLogImpl.INFO, tag, e)
    fun info(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = log(NivelLogImpl.INFO, tag, mensaje, e)

    fun warn(@Size(max = 23) tag: String, mensaje: String) = warn(tag, mensaje, null)
    fun warn(@Size(max = 23) tag: String, e: Throwable) = log(NivelLogImpl.WARN, tag, e)
    fun warn(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = log(NivelLogImpl.WARN, tag, mensaje, e)

    fun error(@Size(max = 23) tag: String, mensaje: String) = error(tag, mensaje, null)
    fun error(@Size(max = 23) tag: String, e: Throwable) = log(NivelLogImpl.ERROR, tag, e)
    fun error(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = log(NivelLogImpl.ERROR, tag, mensaje, e)

    fun log(nivel: NivelLog, @Size(max = 23) tag: String, error: Throwable) =
            log(nivel, tag, error.localizedMessage ?: "", error)

    fun log(nivel: NivelLog, @Size(max = 23) tag: String, mensaje: String) = log(nivel, tag, mensaje, null)

    fun log(nivel: NivelLog, @Size(max = 23) tag: String, mensaje: String, error: Throwable?) {
        val trazaLog = TrazaLog(nivel, tag, mensaje, error)
        internalLog(trazaLog)
    }

    private fun internalLog(trazaLog: TrazaLog) {
        observers.forEach {
            it.log(trazaLog)
        }
    }

    fun t(@Size(max = 23) tag: String, mensaje: String) = trace(tag, mensaje)
    fun t(@Size(max = 23) tag: String, e: Throwable) = trace(tag, e)
    fun t(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = trace(tag, mensaje, e)
    fun d(@Size(max = 23) tag: String, mensaje: String) = debug(tag, mensaje)
    fun d(@Size(max = 23) tag: String, e: Throwable) = debug(tag, e)
    fun d(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = debug(tag, mensaje, e)
    fun i(@Size(max = 23) tag: String, mensaje: String) = info(tag, mensaje)
    fun i(@Size(max = 23) tag: String, e: Throwable) = info(tag, e)
    fun i(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = info(tag, mensaje, e)
    fun w(@Size(max = 23) tag: String, mensaje: String) = warn(tag, mensaje)
    fun w(@Size(max = 23) tag: String, e: Throwable) = warn(tag, e)
    fun w(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = warn(tag, mensaje, e)
    fun e(@Size(max = 23) tag: String, mensaje: String) = error(tag, mensaje)
    fun e(@Size(max = 23) tag: String, e: Throwable) = error(tag, e)
    fun e(@Size(max = 23) tag: String, mensaje: String, e: Throwable?) = error(tag, mensaje, e)
}