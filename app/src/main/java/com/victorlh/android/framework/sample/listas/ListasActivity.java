package com.victorlh.android.framework.sample.listas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.victorlh.android.framework.listas.ItemLista;
import com.victorlh.android.framework.listas.adapter.AbstractViewHolder;
import com.victorlh.android.framework.listas.adapter.ListaAdapter;
import com.victorlh.android.framework.listas.view.ListaView;
import com.victorlh.android.framework.sample.R;
import com.victorlh.android.framework.sample.listas.shimmer_minimo.ListaShimmerMinimoActivity;
import com.victorlh.android.framework.sample.listas.simple.ListaSimpleActivity;

public class ListasActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listas);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		initLista();
	}

	private void initLista() {
		ListaView listaView = findViewById(android.R.id.list);
		Lista lista = new Lista();
		lista.setLista(ItemListas.values());
		listaView.setAdapter(lista);

		lista.setOnClickElementoListener(new ListaAdapter.OnClickElementoListener<ItemListas>() {
			@Override
			public void onClickElemento(AbstractViewHolder<ItemListas> viewHolder) {
				ItemListas item = viewHolder.getItem();
				Intent intent = new Intent(ListasActivity.this, item.aClass);
				startActivity(intent);
			}
		});
	}

	private static final class Lista extends ListaAdapter<ItemListas> {

		private Lista() {
			super(ItemListas.class);
		}

		@Override
		protected AbstractViewHolder<ItemListas> createViewHolder(View view) {
			return new AbstractViewHolder<ItemListas>(view, R.layout.item_boton) {
				@Override
				protected void onProcesar() {
					ItemListas item = getItem();
					TextView btnItem = itemView.findViewById(R.id.btnItem);
					btnItem.setText(item.titulo);
				}
			};
		}
	}

	private enum ItemListas implements ItemLista {

		LISTA_SIMPLE(ListaSimpleActivity.class, R.string.titulo_lista_simple),
		LISTA_SHIMMER_MINIMO(ListaShimmerMinimoActivity.class, R.string.titulo_lista_shimmer_minimo);

		public final Class<? extends AppCompatActivity> aClass;
		@StringRes
		public final int titulo;

		ItemListas(Class<? extends AppCompatActivity> aClass, @StringRes int titulo) {
			this.aClass = aClass;
			this.titulo = titulo;
		}

		@Override
		public long getId() {
			return 0;
		}
	}
}
