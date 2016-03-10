package myplugin.jog;

import java.util.ArrayList;
import java.util.List;

import de.pjog.plugin.Command;
import de.pjog.plugin.Configuration;
import de.pjog.plugin.EventHandler;
import de.pjog.plugin.Plugin;
import de.pjog.plugin.PluginTools;

public class MyClass extends PluginTools implements Plugin {

	List<Command> registeredCommands;
	Configuration config;

	@Override
	public void onLoad() {
		log("Loading " + getName() + " Version: " + getVersion());
		log("Running target: " + System.getProperty("os.name"));

		registeredCommands = new ArrayList<Command>();
		
		config = new Configuration(getName());
		if (!Boolean.parseBoolean(config.get("Enabled"))) {
			onUnload();
			return;
		}

		Command c = new MyCommand();

		if (EventHandler.register(c))
			registeredCommands.add(c);

		log("Loaded!");
	}

	@Override
	public void onUnload() {
		if (!registeredCommands.isEmpty())
			for (Command c : registeredCommands) {
				EventHandler.unregister(c);
			}
		log("Unloaded " + getName());
	}

	@Override
	public String getName() {
		return "MyPlugin";
	}

	@Override
	public String getVersion() {
		return "0.0.0.1";
	}

}

class MyCommand extends PluginTools implements Command {

	@Override
	public String[] getPermissions() {
		return new String[] { "myplugin.mycommand" };
	}

	@Override
	public String getSyntax() {
		return "/mycommand";
	}

	@Override
	public String getName() {
		return "MyCommand";
	}

	@Override
	public String getDescription() {
		return "A toy command";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "myc" };
	}

	@Override
	public boolean onExecute(String[] args) {
		log("Someone executed me!");
		return true;
	}

}
