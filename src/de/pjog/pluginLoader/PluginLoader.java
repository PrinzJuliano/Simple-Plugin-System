package de.pjog.pluginLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import de.pjog.plugin.Plugin;

/**
 * Core functionallity of this System: Loading Plugins.
 * @author PrinzJuliano
 *
 */
public class PluginLoader {

	//Vars
	private ArrayList<Plugin> plugins;
	
	private static String PLUGIN_PATH;
	
	/**
	 * Init with default folder: Plugins
	 */
	public PluginLoader(){
		this("Plugins");
	}
	
	/**
	 * Load all loadable jars and zip files.
	 * @param folder The place where to look for Plugins
	 */
	public PluginLoader(String folder){
		try{
			//Instantiate
			plugins = new ArrayList<Plugin>();
			File fFolder = new File(folder);
			
			//Create folders
			if(!fFolder.exists())
			{
				fFolder.mkdir();
			}
			else if(!fFolder.isDirectory())
			{
				//The folder is a file
				throw new IOException("The path :'" + fFolder.getAbsolutePath() + "' is not a directory");
			}
			
			//Setup for external usage (Configuration)
			PLUGIN_PATH = fFolder.getAbsolutePath();
			
			//Get all jars and zips
			ArrayList<File> names = new ArrayList<File>();			
			for(final File filename : fFolder.listFiles()){
				if(filename.isDirectory()) continue;
				if(!(filename.getName().trim().endsWith("zip") || filename.getName().trim().endsWith("jar"))) continue;
				names.add(filename);
			}
			
			//Load the plugin.properties
			for(int i = 0; i < names.size(); i++)
			{
				JarFile jar = new JarFile(names.get(i));
				InputStream isp = getPropertiesFromZip(jar);
				
				if(isp == null) continue;
				
				Properties props = new Properties();
				props.load(isp);
				
				//Add the Plugin to the global list.
				@SuppressWarnings("deprecation")
				ClassLoader authorizedLoader = URLClassLoader.newInstance(new URL[] { names.get(i).toURL() });
				Plugin authorizedPlugin = (Plugin) authorizedLoader.loadClass(props.getProperty("main")).newInstance();
				plugins.add(authorizedPlugin);
				
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Getter for PLUGIN_PATH
	 * @return PLUGIN_PATH
	 */
	public static String getPluginPath(){
		return PLUGIN_PATH;
	}
	
	/**
	 * Getter for plugins.
	 * @return plugins
	 */
	public ArrayList<Plugin> getPlugins(){
		return plugins;
	}
	
	/**
	 * Get the InputStream to the plugin.properties
	 * @param zip the file
	 * @return InpuStream
	 * @throws ZipException if something went wrong in the zip
	 * @throws IOException reading is difficult.
	 */
	private InputStream getPropertiesFromZip(JarFile zip) throws ZipException, IOException
	{
		
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while(entries.hasMoreElements())
		{
			ZipEntry entry = entries.nextElement();
			if(entry.getName().trim().equalsIgnoreCase("plugin.properties")){
				return zip.getInputStream(entry);
			}
		}
		return null;
	}
}
