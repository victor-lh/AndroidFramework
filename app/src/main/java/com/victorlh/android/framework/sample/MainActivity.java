package com.victorlh.android.framework.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.victorlh.android.framework.listas.AbstractLista;
import com.victorlh.android.framework.listas.AbstractViewHolder;
import com.victorlh.android.framework.listas.IEventosListas;
import com.victorlh.android.framework.listas.ItemLista;
import com.victorlh.android.framework.listas.view.ListaView;
import com.victorlh.android.framework.sample.listas.ListasActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		initLista();
	}

	private void initLista() {
		ListaView listaView = findViewById(android.R.id.list);
		MainLista lista = new MainLista();
		lista.setLista(ItemMain.values());
		listaView.setAdapter(lista);

		lista.setOnClickElementoListener(new IEventosListas.OnClickElementoListener<ItemMain>() {
			@Override
			public void onClickElemento(AbstractViewHolder<ItemMain> viewHolder) {
				ItemMain item = viewHolder.getItem();
				Intent intent = new Intent(MainActivity.this, item.aClass);
				startActivity(intent);
			}
		});
	}

	private static final class MainLista extends AbstractLista<ItemMain> {

		private MainLista() {
			super(R.layout.item_boton, ItemMain.class);
		}

		@Override
		protected AbstractViewHolder<ItemMain> createViewHolder(View view) {
			return new AbstractViewHolder<ItemMain>(view) {
				@Override
				protected void onProcesar() {
					ItemMain item = getItem();
					MaterialButton btnItem = itemView.findViewById(R.id.btnItem);
					btnItem.setText(item.titulo);
				}
			};
		}
	}

	private enum ItemMain implements ItemLista {

		LISTAS(ListasActivity.class, R.string.titulo_listas);

		public final Class<? extends AppCompatActivity> aClass;
		@StringRes
		public final int titulo;

		ItemMain(Class<? extends AppCompatActivity> aClass, @StringRes int titulo) {
			this.aClass = aClass;
			this.titulo = titulo;
		}

		@Override
		public long getId() {
			return 0;
		}
	}
}
