package de.pjog.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import de.pjog.pluginLoader.PluginLoader;

/**
 * A Simple wrapper for java.util.Properties.
 * @author PrinzJuliano
 *
 */
public class Configuration {

	private Properties props;
	private File config;

	/**
	 * Constructor:
	 * <p>
	 * <ol><li>Creates a folder for the plugin and a config.properties</li><li>Adds the Enabled tag with true as value</li></ol>
	 * @param pluginName The name of the plugin the configuratino should be loaded for.
	 */
	public Configuration(String pluginName) {
		props = new Properties();

		try {
			File folder = new File(PluginLoader.getPluginPath() + "/" + pluginName);

			if (!folder.exists() || !folder.isDirectory()) {
				folder.mkdir();
			}

			config = new File(folder.getPath() + "/config.properties");

			if (!config.exists()) {
				try {
					config.createNewFile();
					props.setProperty("Enabled", "true");
					save();
				} catch (Exception e) {

				}
			}

			load();
		} catch (Exception e) {
			System.out.println(PluginTools.ANSI_RED + "Something went wrong while loading the config for" + pluginName);
			e.printStackTrace();
		}

	}

	/**
	 * Wrapper for Properties.getProperty()
	 * @param key the KEY
	 * @return the value or null
	 */
	public String get(String key) {
		return props.getProperty(key);
	}

	/**
	 * Wrapper for Properties.setProperty()
	 * @param key THE KEY
	 * @param value THE VALUE
	 */
	public void set(String key, String value) {
		props.setProperty(key, value);
	}

	/**
	 * Wrapper for Properties.storeToXML()
	 */
	public void save() {
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(config);
			props.storeToXML(o, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Wrapper for Properties.loadFromXML()
	 */
	public void load() {
		FileInputStream in = null;
		try {
			in = new FileInputStream(config);
			props.loadFromXML(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

}
