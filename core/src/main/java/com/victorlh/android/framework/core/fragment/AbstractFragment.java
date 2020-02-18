package com.victorlh.android.framework.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AnimRes;
import androidx.annotation.CallSuper;
import androidx.annotation.ContentView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.victorlh.android.framework.core.flujo.ITransitionStackAnimation;

/**
 * Created by Serlox on 22/11/2017.
 * -
 */
public abstract class AbstractFragment extends Fragment {

	protected View view;
	protected Context context;
	private IEventoFragment lanzadorEvento;

	public AbstractFragment() {
		super();
	}

	@ContentView
	public AbstractFragment(@LayoutRes int contentLayoutId) {
		super(contentLayoutId);
	}

	@Override
	@CallSuper
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	@CallSuper
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.view = view;
		this.context = getContext();
		init(savedInstanceState);
	}

	@CallSuper
	protected void init(@Nullable Bundle savedInstanceState) {
	}

	public void setLanzadorEvento(IEventoFragment lanzadorEvento) {
		this.lanzadorEvento = lanzadorEvento;
	}

	public void lanzarEventoFragment(String eventoCode, IDatoEventoFragment dato) {
		//TODO (Para Victor) - Esto deja de funcionar si se cambia la orientacion
		if (lanzadorEvento != null) {
			lanzadorEvento.onEventoFragment(eventoCode, dato);
		}
	}

	@NonNull
	public final <T extends View> T findViewById(@IdRes int id) {
		if (view == null || id == View.NO_ID) {
			throw new IllegalArgumentException("ID does not reference a View inside this View");
		}
		T viewById = view.findViewById(id);
		if (viewById == null) {
			throw new IllegalArgumentException("ID does not reference a View inside this View");
		}
		return viewById;
	}

	public void setFragment(int layout, AbstractFragment fragment) {
		setFragment(layout, fragment, null);
	}

	public void setFragment(int layout, AbstractFragment fragment, @Nullable ITransitionStackAnimation animation) {
		if (animation != null) {
			setFragment(layout, fragment, animation.getXmlEntrada(), animation.getXmlSalida());
		} else {
			setFragment(layout, fragment, 0, 0);
		}
	}

	public void setFragment(int layout, AbstractFragment fragment, @AnimRes int xmlEntrada, @AnimRes int xmlSalida) {
		if (fragment == null) {
			fragment = new AbstractFragment() {
			};
		}
		FragmentManager fragmentManager = getChildFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (xmlEntrada != 0 && xmlSalida != 0) {
			fragmentTransaction.setCustomAnimations(xmlEntrada, xmlSalida);
		}
		fragmentTransaction.replace(layout, fragment);
		fragmentTransaction.commit();
	}

}
