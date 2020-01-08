package com.victorlh.android.framework.core.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.victorlh.android.framework.core.fragment.AbstractFragment;
import com.victorlh.android.framework.core.fragment.IEventoFragment;

/**
 * @author Victor
 * 09/06/2018
 */
public abstract class AbstractDialogFragment extends DialogFragment implements IDialog, IEventoFragment {

	private int viewId;
	protected View view;

	private AbstractFragment fragment;
	private FragmentManager fragmentManager;

	protected EventosDialogs.OnCancelListener onCancelListener;
	private EventosDialogs.OnDissmissListener onDissmissListener;

	private int height;
	private int width;

	public AbstractDialogFragment() {
		this.width = ViewGroup.LayoutParams.MATCH_PARENT;
		this.height = ViewGroup.LayoutParams.MATCH_PARENT;
	}

	public void init(int viewId, AbstractFragment fragment, FragmentManager fragmentManager) {
		this.viewId = viewId;
		this.fragment = fragment;
		this.fragmentManager = fragmentManager;
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("viewId", viewId);
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			this.viewId = savedInstanceState.getInt("viewId");
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (viewId == 0) {
			return null;
		}
		this.view = inflater.inflate(viewId, container, false);

		if (fragment != null) {
			fragment.setLanzadorEvento(this);
			FragmentManager childFragmentManager = getChildFragmentManager();
			FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
			fragmentTransaction.add(getIdLyFragmentBody(), fragment);
			fragmentTransaction.commit();
		}

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		Dialog dialog = getDialog();
		if (dialog != null) {
			Window window = dialog.getWindow();
			if (window != null) {
				window.setLayout(width, height);
			}

			if (onCancelListener != null) {
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (onCancelListener != null) {
							onCancelListener.onCancel(AbstractDialogFragment.this);
						}
					}
				});
				dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if (onCancelListener != null) {
							onCancelListener.onCancel(AbstractDialogFragment.this);
						}
					}
				});
			}
		}
	}

	protected abstract int getIdLyFragmentBody();

	@Override
	public void show() {
		if (fragmentManager != null) {
			String tag = fragment == null ? "" : fragment.getClass().getName();
			show(fragmentManager, tag);
		}
	}

	@Override
	public void close() {
		dismiss();
	}

	@Override
	public void setOnCancelListener(EventosDialogs.OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	@Override
	public void setOnDissmissListener(EventosDialogs.OnDissmissListener onDissmissListener) {
		this.onDissmissListener = onDissmissListener;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
