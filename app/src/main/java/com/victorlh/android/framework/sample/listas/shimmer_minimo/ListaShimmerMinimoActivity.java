package com.victorlh.android.framework.sample.listas.shimmer_minimo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.adapter.ListaAdapter;
import com.victorlh.android.framework.listas.view.ListaView;
import com.victorlh.android.framework.sample.R;

import java.util.ArrayList;

public class ListaShimmerMinimoActivity extends AppCompatActivity {

	private int control = 4;
	private ListaShimmerMinimoAdapter lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_shimmer_minimo);

		ListaView listaView = findViewById(android.R.id.list);
		lista = new ListaShimmerMinimoAdapter();
		listaView.setAdapter(lista);

		lista.setOnClickElementoListener(new ListaAdapter.OnClickElementoListener<ItemListaShimmerMinimo>() {
			@Override
			public void onClickElemento(AbstractViewHolder<ItemListaShimmerMinimo> viewHolder) {
				Toast.makeText(ListaShimmerMinimoActivity.this, "Elemento: " + viewHolder.getItem().getNumber(), Toast.LENGTH_SHORT).show();
			}
		});

		listaView.startShimmer();
		loadInitData();
	}

	public void addElemento(View view) {
		lista.addItem(new ItemListaShimmerMinimo(++control));
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
				for (int i = 1; i <= control; i++) {
					listaElementos.add(new ItemListaShimmerMinimo(i));
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
