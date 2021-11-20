package com.victorlh.android.framework.core.aplicacion

import android.app.Application
import androidx.annotation.CallSuper

open class Aplicacion : Application() {

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        AplicacionController.inicializar(this)
    }
}