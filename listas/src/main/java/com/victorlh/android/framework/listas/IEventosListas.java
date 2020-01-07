package com.victorlh.android.framework.listas;

/**
 * @author Victor
 * 09/11/2018
 */
@Deprecated
public interface IEventosListas {

	interface OnClickElementoListener<T extends ItemLista> {
		void onClickElemento(AbstractViewHolder<T> viewHolder);
	}

	interface OnLongClickElementoListener<T extends ItemLista> {
		boolean onLongClickElemento(AbstractViewHolder<T> viewHolder);
	}

	interface OnStartSeleccionListener {
		void onStartSeleccion();
	}

	interface OnStopSeleccionListener {
		void onStopSeleccion();
	}

	interface OnSeleccionChangeListener {
		void onSeleccionChangeListener(int numSeleccionados);
	}

	interface OnDataListChangeListener {
		void onSetData();

		void onVoidData();

		void onChangeData();
	}
}
