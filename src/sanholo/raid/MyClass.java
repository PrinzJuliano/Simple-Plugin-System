package sanholo.raid;

import java.util.ArrayList;
import java.util.List;

import de.pjog.plugin.Command;
import de.pjog.plugin.EventHandler;
import de.pjog.plugin.Plugin;
import de.pjog.plugin.PluginTools;

public class MyClass extends PluginTools implements Plugin {

	List<Command> registeredCommands;

	@Override
	public void onLoad() {
		log("Loading " + getName() + " Version: " + getVersion());
		log("Running target: " + System.getProperty("os.name"));

		registeredCommands = new ArrayList<Command>();

		Command c = new MyCommand();

		if (EventHandler.register(c))
			registeredCommands.add(c);

		log("Loaded!");
	}

	@Override
	public void onUnload() {
		for (Command c : registeredCommands) {
			EventHandler.unregister(c);
		}
		log("Unloaded " + getName());
	}

	@Override
	public String getName() {
		return "SanHoloRaid";
	}

	@Override
	public String getVersion() {
		return "0.0.0.1";
	}

}

class MyCommand extends PluginTools implements Command {

	public boolean raidStatus = false;

	@Override
	public String[] getPermissions() {
		return new String[] { "raid.execute" };
	}

	@Override
	public String getSyntax() {
		return "/raid [start | stop | status]";
	}

	@Override
	public String getName() {
		return "Raid";
	}

	@Override
	public String getDescription() {
		return "A toy command";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "mycommand", "myc" };
	}

	@Override
	public boolean onExecute(String[] args) {
		if (args.length >= 2) {
			if (args[1].equalsIgnoreCase("start")) {
				if (raidStatus) {
					log("The raid is already started!");
					return true;
				}
				log("You started a raid!");
				raidStatus = true;
				return true;
			} else if (args[1].equalsIgnoreCase("stop")) {
				if (raidStatus) {
					log("You stopped the raid");
					raidStatus = false;
					return true;
				}
				log("There is no raid to be stopped!");
				return true;
			} else if (args[1].equalsIgnoreCase("status")) {
				log("The raid is " + (raidStatus ? "in progress." : "in preperation."));
				return true;
			}
			else {
				log(ANSI_RED + "Invalid syntax!");
				log(ANSI_RED + "Usage: " + getSyntax());
				return false;
			}

		} else {
			log("The raid is " + (raidStatus ? "in progress." : "in preperation."));
			return true;
		}

	}

}
