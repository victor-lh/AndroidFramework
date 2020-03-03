package com.victorlh.android.framework.logger

import java.io.PrintWriter
import java.io.StringWriter

data class TrazaLog internal constructor(val nivel: NivelLog, val tag: String, val mensaje: String, val error: Throwable?) {

    val stringError: String
        get() = error?.let { getStringError(it) } ?: ""

    companion object {
        private fun getStringError(e: Throwable): String {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            return sw.toString()
        }
    }

}