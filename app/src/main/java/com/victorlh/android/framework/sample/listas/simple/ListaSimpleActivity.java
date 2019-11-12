package com.victorlh.android.framework.sample.listas.simple;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.victorlh.android.framework.listas.view.ListaView;
import com.victorlh.android.framework.sample.R;

public class ListaSimpleActivity extends AppCompatActivity {

	private int control = 0;
	private AbstractListaSimple lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_simple);

		ListaView listaView = findViewById(android.R.id.list);
		lista = new AbstractListaSimple();
		listaView.setAdapter(lista);
	}

	public void addElemento(View view) {
		lista.addItem(new ItemListaSimple(++control));
	}

	public void removeElemento(View view) {
		ItemListaSimple itemById = lista.getItemById(control--);
		lista.deleteItem(itemById);
	}
}
