package com.victorlh.android.framework.core.flujo;

import androidx.annotation.Nullable;

public interface IElementoStackFlujo {

	Object getElemento();

	@Nullable
	ITransitionStackAnimation getAnimacion();

	@Nullable
	ITransitionStackAnimation getAnimacionContraria();

}
