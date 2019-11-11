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

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.victorlh.android.framework.listas.AbstractLista;
import com.victorlh.android.framework.listas.IEventosListas;
import com.victorlh.android.framework.listas.R;
import com.victorlh.android.framework.listas.view.shimmer.ShimmerListView;

/**
 * @author Victor
 * 26/12/2018
 */
public class ListaView extends LinearLayout implements IEventosListas.OnDataListChangeListener {

	private RecyclerView lista;
	private ShimmerListView listaShimmer;
	private ViewGroup lyListaVacia;

	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	private boolean hasShimmer;

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

		ImageView imagen = view.findViewById(R.id.listaRFImagenVacia);
		TextView texto = view.findViewById(R.id.listaRFTextoVacia);

		if (attrs != null) {
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListaView);
			String textoListaVacia = typedArray.getString(R.styleable.ListaView_textoListaVacia);
			Drawable imagenListaVacia = typedArray.getDrawable(R.styleable.ListaView_imagenListaVacia);

			texto.setText(textoListaVacia);
			imagen.setImageDrawable(imagenListaVacia);


			hasShimmer = typedArray.getBoolean(R.styleable.ListaView_hasShimmer, false);
			int numShimmer = typedArray.getInteger(R.styleable.ListaView_numShimmers, 8);
			int layoutShimmerId = typedArray.getResourceId(R.styleable.ListaView_layoutShimmer, 0);
			listaShimmer.setItemCount(numShimmer);
			listaShimmer.setLyShimmer(layoutShimmerId);

			typedArray.recycle();
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
		setAdapter(adapter, layoutManager, null, null, null);
	}

	public void setAdapterDraggable(@NonNull RecyclerView.Adapter adapter) {
		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
		RecyclerViewDragDropManager dragDropManager = new RecyclerViewDragDropManager();
		DraggableItemAnimator draggableItemAnimator = new DraggableItemAnimator();
		setAdapter(adapter, layoutManager, null, dragDropManager, draggableItemAnimator);
	}

	protected void setAdapter(@NonNull RecyclerView.Adapter adapter, @NonNull RecyclerView.LayoutManager layoutManager, RecyclerViewExpandableItemManager manager, RecyclerViewDragDropManager dragDropManager, SimpleItemAnimator animator) {
		this.adapter = adapter;
		this.layoutManager = layoutManager;

		if (adapter instanceof AbstractLista) {
			((AbstractLista) adapter).setOnDataListChangeListener(this);
		}

		if (manager != null) {
			adapter = manager.createWrappedAdapter(adapter);
		}

		if (dragDropManager != null) {
			adapter = dragDropManager.createWrappedAdapter(adapter);
			dragDropManager.setCheckCanDropEnabled(false);
		}

		if (animator == null) {
			animator = new RefactoredDefaultItemAnimator();
		}
		animator.setSupportsChangeAnimations(false);

		lista.setLayoutManager(layoutManager);
		lista.setAdapter(adapter);
		lista.setItemAnimator(animator);
		lista.setHasFixedSize(false);

		if (dragDropManager != null) {
			dragDropManager.attachRecyclerView(lista);
		}
		if (manager != null) {
			manager.attachRecyclerView(lista);
		}
		loadListaEmpty();
	}

	private void loadListaEmpty() {
		if (getListCount() <= 0) {
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

	protected int getListCount() {
		return adapter == null ? 0 : adapter.getItemCount();
	}

	public void setLayoutShimmerId(@LayoutRes int layoutShimmerId) {
		listaShimmer.setLyShimmer(layoutShimmerId);
	}

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
