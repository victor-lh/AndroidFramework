package com.victorlh.android.framework.listas.adapter.seleccionable;

import androidx.annotation.NonNull;

import com.victorlh.android.framework.listas.ItemLista;

import java.util.List;

public interface ListaSeleccionable<T extends ItemLista> {

	enum ETipoSeleccion {
		NONE, CLICK_UNICO, CLICK_MULTIPLE, LONGCLICK
	}

	void setTipoSeleccion(@NonNull ETipoSeleccion tipoSeleccion);

	ETipoSeleccion getTipoSeleccion();

	void setSeleccion(@NonNull T[] items);

	void setSeleccion(@NonNull List<T> items);

	void addSeleccion(@NonNull T item);

	void removeSeleccion(@NonNull T item);

	void removeAllSeleccion();

	boolean haySeleccionados();

	boolean isSeleccionado(@NonNull T item);

	T[] getItemsSeleccionados();

	void addOnStartSeleccionListener(@NonNull OnStartSeleccionListener onStartSeleccionListener);

	void addOnStopSeleccionListener(@NonNull OnStopSeleccionListener onStopSeleccionListener);

	void addOnSeleccionChangeListener(@NonNull OnSeleccionChangeListener onSeleccionChangeListener);

	interface OnStartSeleccionListener {
		void onStartSeleccion();
	}

	interface OnStopSeleccionListener {
		void onStopSeleccion();
	}

	interface OnSeleccionChangeListener {
		void onSeleccionChangeListener(int numSeleccionados);
	}
}
