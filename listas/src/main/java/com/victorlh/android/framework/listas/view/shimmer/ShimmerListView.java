package com.victorlh.android.framework.listas.view.shimmer;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

public class ShimmerListView extends ShimmerFrameLayout {

	private static final int DEFAULT_ITEM_COUNT = 8;

	private RecyclerView lista;

	private int itemCount = DEFAULT_ITEM_COUNT;
	private int lyShimmer = -1;
	private RecyclerView.LayoutManager layoutManager;

	public ShimmerListView(Context context) {
		super(context);
		init();
	}

	public ShimmerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ShimmerListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public ShimmerListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		this.lista = new RecyclerView(getContext());
		RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		this.lista.setLayoutParams(layoutParams);
		addView(lista);
		loadAdapter();
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
		loadAdapter();
	}

	public void setLyShimmer(int lyShimmer) {
		this.lyShimmer = lyShimmer;
		loadAdapter();
	}

	public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
		loadAdapter();
	}

	private void loadAdapter() {
		if (itemCount >= 0 || lyShimmer >= 0) {
			ShimmerAdapter adapter = new ShimmerAdapter();
			RecyclerView.LayoutManager layoutManager = this.layoutManager;
			if (layoutManager == null) {
				layoutManager = new LinearLayoutManager(getContext());
			}
			lista.setLayoutManager(layoutManager);
			lista.setAdapter(adapter);
		}
	}

	private final class ShimmerAdapter extends RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder> {

		private ShimmerAdapter() {
		}

		@NonNull
		@Override
		public ShimmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(lyShimmer, parent, false);
			return new ShimmerViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull ShimmerViewHolder holder, int position) {
		}

		@Override
		public int getItemCount() {
			return itemCount;
		}

		private final class ShimmerViewHolder extends RecyclerView.ViewHolder {
			private ShimmerViewHolder(@NonNull View itemView) {
				super(itemView);
			}
		}
	}
}
