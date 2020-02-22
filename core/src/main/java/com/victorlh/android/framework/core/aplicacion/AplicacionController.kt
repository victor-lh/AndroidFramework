package com.victorlh.android.framework.core.aplicacion

import android.app.Application

object AplicacionController {

    private var inicializado = false

    @Synchronized
    fun inicializar(application: Application) {
        if (!inicializado) {
            inicializado = true
        }
    }

    fun checkInicializado() {
        check(inicializado) { "No se ha inicializado el AplicacionController" }
    }
}