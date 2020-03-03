package com.victorlh.android.framework.core.flujo

import androidx.annotation.AnimRes

interface ITransitionStackAnimation {

    @get:AnimRes
    val xmlEntrada: Int

    @get:AnimRes
    val xmlSalida: Int
}