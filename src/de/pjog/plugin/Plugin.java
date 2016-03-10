package de.pjog.plugin;

/**
 * 
 * @author PrinzJuliano
 *
 */
public interface Plugin {
	
	/**
	 * All your initialization should go in here
	 */
	public void onLoad();
	
	/**
	 * All deinit should go in here
	 */
	public void onUnload();
	
	/**
	 * The Name of the plugin.
	 * @return The name
	 */
	public String getName();
	
	/**
	 * The Version of your plugin.
	 * @return The Version.
	 */
	public String getVersion();
}
