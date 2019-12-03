package com.victorlh.android.framework.listas;

import android.view.View;

import androidx.annotation.NonNull;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemState;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.draggable.annotation.DraggableItemStateFlags;

/**
 * @author Victor
 * 17/12/2018
 */
@Deprecated
public abstract class AbstractViewHolderDraggable<T extends ItemLista> extends AbstractViewHolder<T> implements DraggableItemViewHolder {

	private final DraggableItemState mDragState = new DraggableItemState();

	public AbstractViewHolderDraggable(View itemView) {
		super(itemView);
	}

	@Override
	public void setDragStateFlags(@DraggableItemStateFlags int flags) {
		mDragState.setFlags(flags);
	}

	@Override
	@DraggableItemStateFlags
	public int getDragStateFlags() {
		return mDragState.getFlags();
	}

	@Override
	@NonNull
	public DraggableItemState getDragState() {
		return mDragState;
	}

	public abstract View getViewDragButton();

	public abstract View getViewDragContainer();

	public boolean isDragActivate(int x, int y) {
		View viewDragContainer = getViewDragContainer();
		final View dragHandleView = getViewDragButton();

		final int offsetX = viewDragContainer.getLeft() + (int) (viewDragContainer.getTranslationX() + 0.5f);
		final int offsetY = viewDragContainer.getTop() + (int) (viewDragContainer.getTranslationY() + 0.5f);

		return hitTest(dragHandleView, x - offsetX, y - offsetY);
	}

	private static boolean hitTest(View v, int x, int y) {
		final int tx = (int) (v.getTranslationX() + 0.5f);
		final int ty = (int) (v.getTranslationY() + 0.5f);
		final int left = v.getLeft() + tx;
		final int right = v.getRight() + tx;
		final int top = v.getTop() + ty;
		final int bottom = v.getBottom() + ty;

		return (x >= left) && (x <= right) && (y >= top) && (y <= bottom);
	}
}
