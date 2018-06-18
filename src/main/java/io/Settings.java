package main.java.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*
 * Patrón Singleton
 */
public class Settings {

	private static final String CONF_FILE = "src/main/resources/genetico/configuracion.properties";
	
	private static Settings instance;
	private Properties properties;

	private Settings() {
		properties = new Properties();
		try {
			properties.load(new FileReader(CONF_FILE));
		} catch (IOException e) {
			throw new RuntimeException("Propeties file can not be loaded", e);
		}
	}
	
	public static String get(String key) {
		return getInstance().getProperty( key );
	}

	private String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new RuntimeException("Property not found in config file");
		}
		return value;
	}

	private static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

}
