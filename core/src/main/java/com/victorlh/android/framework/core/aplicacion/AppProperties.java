package com.victorlh.android.framework.core.aplicacion;

import android.content.Context;

import com.victorlh.tools.ToolsValidacion;
import com.victorlh.tools.Transform;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

	private Properties properties;

	AppProperties(Context context) {
		try {
			InputStream inputStream = context.getAssets().open("app.properties");
			this.properties = new Properties();
			this.properties.load(inputStream);
		} catch (IOException e) {
			this.properties = null;
			//TODO - Log de no hay fichero properties
		}
	}

	private static AppProperties getInstance() {
		AplicacionController aplicacionController = AplicacionController.getInstance();
		return aplicacionController.getAppProperties();
	}

	private String getStringProperty(String propertieName, String defecto) {
		checkExist();
		String property = properties.getProperty(propertieName);
		return ToolsValidacion.isCadenaNoVacia(property) ? property : defecto;
	}

	private boolean getBooleanProperty(String propertieName, boolean defecto) {
		checkExist();
		String property = properties.getProperty(propertieName);
		return ToolsValidacion.isCadenaNoVacia(property) ? Transform.toBoolean(property) : defecto;
	}

	private String getStringProperty(String propertieName) {
		return getProperty(propertieName, "");
	}

	private boolean getBooleanProperty(String propertieName) {
		return getBooleanProperty(propertieName, false);
	}

	public static String getProperty(String propertieName, String defecto) {
		AppProperties instance = AppProperties.getInstance();
		return instance.getStringProperty(propertieName, defecto);
	}

	public static String getProperty(String propertieName) {
		AppProperties instance = AppProperties.getInstance();
		return instance.getStringProperty(propertieName);
	}

	public static boolean getPropertyBoolean(String propertieName, boolean defecto) {
		AppProperties instance = AppProperties.getInstance();
		return instance.getBooleanProperty(propertieName, defecto);
	}

	public static boolean getPropertyBoolean(String propertieName) {
		AppProperties instance = AppProperties.getInstance();
		return instance.getBooleanProperty(propertieName);
	}

	private void checkExist() {
		if (properties == null) {
			throw new IllegalStateException("No existe o no se encuentra el fichero app.properties");
		}
	}
}
