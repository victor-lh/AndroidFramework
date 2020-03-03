package com.victorlh.android.framework.core.flujo

interface IElementoStackFlujo {

    val elemento: Any?
    val animacion: ITransitionStackAnimation?
    val animacionContraria: ITransitionStackAnimation?
}