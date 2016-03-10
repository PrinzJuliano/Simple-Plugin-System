package de.pjog.pluginLoader;

import java.util.ArrayList;

import de.pjog.plugin.Plugin;
import de.pjog.plugin.PluginTools;

/**
 * All plugins combined in a Manager class.
 * @author PrinzJuliano
 *
 */
public class PluginManager implements Plugin{
	
	private ArrayList<Plugin> plugins;
	private PluginLoader loader;
	
	public PluginManager(){
		loader = new PluginLoader();
		plugins = loader.getPlugins();
	}

	@Override
	public void onLoad() {
		for(Plugin p : plugins)
		{
			p.onLoad();
		}
		System.out.println(PluginTools.ANSI_RESET + PluginTools.ANSI_ITALIC + PluginTools.ANSI_GREEN + "Loaded Version " + getVersion() + " of " + getName() + PluginTools.ANSI_RESET);
	}

	@Override
	public void onUnload() {
		for(Plugin p : plugins)
		{
			p.onUnload();
		}
		System.out.println(PluginTools.ANSI_RESET + PluginTools.ANSI_ITALIC + PluginTools.ANSI_GREEN + "All plugins were unloaded!" + PluginTools.ANSI_RESET);
		
	}

	@Override
	public String getName() {
		String sPlugins = "";
		for(int i = 0; i < plugins.size(); i++)
		{
			sPlugins += plugins.get(i).getName();
			if(i < plugins.size()-1)
				sPlugins += ", ";
		}
		return "MasterPlugin with: " + sPlugins;
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

}
