package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.victorlh.android.framework.listas.adapter.AbstractLista;
import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.view.ListaView;
import com.victorlh.android.framework.sample.R;
import com.victorlh.tools.cifrado.AlfabetoExcel;

import java.util.ArrayList;

public class ListaShimmerMinimoActivity extends AppCompatActivity {

	private int control = 3;
	private AbstractListaShimmerMinimo lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_shimmer_minimo);

		ListaView listaView = findViewById(android.R.id.list);
		lista = new AbstractListaShimmerMinimo();
		lista.setKeyOrdenacion(ItemListaShimmerMinimo.Ordenacion.A);
		listaView.setAdapter(lista);

		lista.setOnClickElementoListener(new AbstractLista.OnClickElementoListener<ItemListaShimmerMinimo>() {
			@Override
			public void onClickElemento(AbstractViewHolder<ItemListaShimmerMinimo> viewHolder) {
				Toast.makeText(ListaShimmerMinimoActivity.this, "Click: " + viewHolder.getItem().getText(), Toast.LENGTH_SHORT).show();
			}
		});

		lista.setOnLongClickElementoListener(new AbstractLista.OnLongClickElementoListener<ItemListaShimmerMinimo>() {
			@Override
			public boolean onLongClickElemento(AbstractViewHolder<ItemListaShimmerMinimo> viewHolder) {
				Toast.makeText(ListaShimmerMinimoActivity.this, "LongClick: " + viewHolder.getItem().getText(), Toast.LENGTH_SHORT).show();
				return true;
			}
		});

		listaView.startShimmer();
		loadInitData();
	}

	public void addElemento(View view) {
//		ItemListaShimmerMinimo itemById = lista.getItemById(1);
//		ItemListaShimmerMinimo itemListaShimmerMinimo = new ItemListaShimmerMinimo(itemById.getId(), AlfabetoExcel.toLetraAlfabeto(++control));
//		lista.setItem(itemListaShimmerMinimo);
		int i = ++control;
		ItemListaShimmerMinimo itemListaShimmerMinimo = new ItemListaShimmerMinimo(i, AlfabetoExcel.toLetraAlfabeto(i));
		lista.addItem(itemListaShimmerMinimo,1);
	}

	public void removeElemento(View view) {
		ItemListaShimmerMinimo itemById = lista.getItemById(control--);
		lista.deleteItem(itemById);
	}

	private void loadInitData() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ignored) {
				}

				final ArrayList<ItemListaShimmerMinimo> listaElementos = new ArrayList<>();
				for (int i = 0; i <= control; i++) {
					listaElementos.add(new ItemListaShimmerMinimo(i, AlfabetoExcel.toLetraAlfabeto(i)));
				}

				runOnUiThread(new Runnable() {
					public void run() {
						lista.setLista(listaElementos);
					}
				});
			}
		}).start();
	}

}
