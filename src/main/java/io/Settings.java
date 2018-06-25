package main.java.io;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/*
 * Patrón de diseño Singleton
 */
public class Settings {

	private static final Path CONF_FILE =
            Paths.get("src","main","resources","genetico", "configuracion.properties");

	
	private static Settings instance;
	private Properties properties;

	private Settings() {
	File f = new File(CONF_FILE.toUri());
		properties = new Properties();
		try {
			properties.load(new FileReader(f));
		} catch (IOException e) {
			throw new RuntimeException("Propeties file can not be loaded", e);
		}
	}
	
	public static String get(String key) {
		return getInstance().getProperty( key );
	}

	public static int getInteger(String key) {
        try {
            return Integer.parseInt( get(key) );
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Fichero configuration.properties: la propiedad " + key +
                    " no contenia un valor numerico (entero)");
        }
    }

    public static float getFloat(String key) {
	    try {
            return Float.parseFloat( get(key) );
        }catch (NumberFormatException ex){
            throw new RuntimeException("Fichero configuration.properties: la propiedad " + key +
                    " no contenia un valor numerico (float)");
        }
    }

    public static boolean getBoolean(String key) {
        try {
            return Boolean.parseBoolean( get(key) );
        }catch (NumberFormatException ex){
            throw new RuntimeException("Fichero configuration.properties: la propiedad " + key +
                    " no contenia un valor booleano (true/false)");
        }

    }


        private String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new RuntimeException("Property "+key+" not found in config file");
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
