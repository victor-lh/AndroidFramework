package com.victorlh.android.framework.core.activitys;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.victorlh.android.framework.core.flujo.ITransitionStackAnimation;
import com.victorlh.android.framework.core.fragment.AbstractFragment;
import com.victorlh.android.framework.core.idioma.IIdioma;

import java.util.Locale;

/**
 * @author Victor
 * 02/06/2018
 */
public abstract class AbstractActivity extends AppCompatActivity {

	protected Context context;

	public AbstractActivity(int contentLayoutId) {
		super(contentLayoutId);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		establecerIdioma();
	}

	protected abstract IIdioma getIdiomaAplicacion();

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
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (xmlEntrada != 0 && xmlSalida != 0) {
			fragmentTransaction.setCustomAnimations(xmlEntrada, xmlSalida);
		}
		fragmentTransaction.replace(layout, fragment);
		fragmentTransaction.commit();
	}

	private void establecerIdioma() {
		IIdioma eIdioma = getIdiomaAplicacion();
		Locale locale = eIdioma.getLocale();
		Locale.setDefault(locale);
		Resources res = this.getResources();
		Configuration config = new Configuration();
		config.locale = locale;
		res.updateConfiguration(config, res.getDisplayMetrics());
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}
}
