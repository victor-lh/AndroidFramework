package com.victorlh.android.framework.listas.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.victorlh.android.framework.listas.R;
import com.victorlh.android.framework.listas.adapter.AbstractLista;
import com.victorlh.android.framework.listas.view.shimmer.ShimmerListView;

/**
 * @author Victor
 * 26/12/2018
 */
public class ListaView extends LinearLayout {

	private RecyclerView lista;
	private ShimmerListView listaShimmer;
	private ViewGroup lyListaVacia;
	private ImageView ivEmpty;
	private TextView tvEmpty;

	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	private boolean hasShimmer;

	private int voidItemCount;


	public ListaView(Context context) {
		super(context);
		init(context, null);
	}

	public ListaView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ListaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, @Nullable AttributeSet attrs) {
		View view = inflate(context, R.layout.view_listas, this);

		this.lista = view.findViewById(R.id.listaRFList);
		this.listaShimmer = view.findViewById(R.id.listaRFShimmer);
		lista.setNestedScrollingEnabled(false);
		this.lyListaVacia = view.findViewById(R.id.listaRfLyVacia);
		ivEmpty = view.findViewById(R.id.listaRFImagenVacia);
		tvEmpty = view.findViewById(R.id.listaRFTextoVacia);

		if (attrs != null) {
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListaView);
			String textoListaVacia = typedArray.getString(R.styleable.ListaView_textoListaVacia);
			Drawable imagenListaVacia = typedArray.getDrawable(R.styleable.ListaView_imagenListaVacia);
			this.voidItemCount = typedArray.getInteger(R.styleable.ListaView_voidItemCount, 0);

			this.hasShimmer = typedArray.getBoolean(R.styleable.ListaView_hasShimmer, false);
			int numShimmer = typedArray.getInteger(R.styleable.ListaView_numShimmers, 8);
			int layoutShimmerId = typedArray.getResourceId(R.styleable.ListaView_layoutShimmer, 0);

			typedArray.recycle();

			setTextoEmpty(textoListaVacia);
			setImagenEmpty(imagenListaVacia);

			setShimmerCount(numShimmer);
			setLayoutShimmerId(layoutShimmerId);
		}
	}

	public RecyclerView.Adapter getAdapter() {
		return adapter;
	}

	public void setAdapter(@NonNull RecyclerView.Adapter adapter) {
		Context context = getContext();
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		setAdapter(adapter, layoutManager);
	}

	public void setAdapter(@NonNull RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
		setAdapter(adapter, layoutManager, null);
	}

	public void setAdapter(@NonNull RecyclerView.Adapter adapter, @NonNull RecyclerView.LayoutManager layoutManager, RecyclerView.ItemAnimator animator) {
		this.adapter = adapter;
		this.layoutManager = layoutManager;

		if (adapter instanceof AbstractLista) {
			((AbstractLista) adapter).setOnDataListChangeListener(new ListaOnDataChangeAdapter());
		}

		if (animator == null) {
			animator = new RefactoredDefaultItemAnimator();
		}

		if (voidItemCount > 0) {
			ListaMinElementosManager listaMinElementosManager = new ListaMinElementosManager(voidItemCount);
			adapter = listaMinElementosManager.wrapAdapter(adapter);
		}

		lista.setLayoutManager(layoutManager);
		lista.setAdapter(adapter);
		lista.setItemAnimator(animator);
		lista.setHasFixedSize(false);

		loadListaEmpty();
	}

	private void loadListaEmpty() {
		if (isEmpty()) {
			lista.setVisibility(GONE);
			listaShimmer.setVisibility(GONE);
			lyListaVacia.setVisibility(VISIBLE);
		} else {
			listaShimmer.setVisibility(GONE);
			lyListaVacia.setVisibility(GONE);

			lista.setLayoutManager(layoutManager);
			lista.setVisibility(VISIBLE);
		}
	}

	public void startShimmer() {
		stopShimmer();
		if (hasShimmer) {
			lyListaVacia.setVisibility(GONE);
			lista.setVisibility(GONE);
			listaShimmer.setVisibility(VISIBLE);

			lista.setLayoutManager(null);
			listaShimmer.setLayoutManager(layoutManager);
			listaShimmer.startShimmer();
		}
	}

	public void stopShimmer() {
		listaShimmer.stopShimmer();
		listaShimmer.setVisibility(GONE);
		listaShimmer.setLayoutManager(null);

		loadListaEmpty();
	}

	protected boolean isEmpty() {
		RecyclerView.Adapter adapter = lista.getAdapter();
		return adapter == null || adapter.getItemCount() <= 0;
	}

	public void setTextoEmpty(String textoEmpty) {
		tvEmpty.setText(textoEmpty);
	}

	public void setTextoEmpty(@StringRes int textoEmpty) {
		tvEmpty.setText(textoEmpty);
	}

	public void setImagenEmpty(@DrawableRes int imagenEmpty) {
		ivEmpty.setImageResource(imagenEmpty);
	}

	public void setImagenEmpty(Drawable imagenEmpty) {
		ivEmpty.setImageDrawable(imagenEmpty);
	}

	public void setVoidItemCount(int voidItemCount) {
		this.voidItemCount = voidItemCount;
	}

	public void setHasShimmer(boolean hasShimmer) {
		this.hasShimmer = hasShimmer;
	}

	public void setShimmerCount(int shimmerCount) {
		listaShimmer.setItemCount(shimmerCount);
	}

	public void setLayoutShimmerId(@LayoutRes int layoutShimmerId) {
		listaShimmer.setLyShimmer(layoutShimmerId);
	}

	private final class ListaOnDataChangeAdapter implements AbstractLista.OnDataListChangeListener {

		@Override
		public void onSetData() {
			stopShimmer();
		}

		@Override
		public void onVoidData() {
			stopShimmer();
		}

		@Override
		public void onChangeData() {
			stopShimmer();
		}
	}
}
