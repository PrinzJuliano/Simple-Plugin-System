package de.pjog.pluginLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.pjog.plugin.Command;
import de.pjog.plugin.EventHandler;
import de.pjog.plugin.PluginTools;

/**
 * A basic/barebones implementation of this system. 
 * @author PrinzJuliano
 *
 */
public class Main {

	public static PluginManager Instance;
	public static EventHandler eventHandler;

	private static boolean running = true;

	public static void main(String[] args) {
		System.out.println(PluginTools.ANSI_CYAN + "Main System booting up . . .");
		eventHandler = new EventHandler();
		Instance = new PluginManager();
		Instance.onLoad();

		EventHandler.register(new Command(){

			@Override
			public String[] getPermissions() {
				return null;
			}

			@Override
			public String getSyntax() {
				return "/exit";
			}

			@Override
			public String getName() {
				return "exit";
			}

			@Override
			public String[] getAliases() {
				return null;
			}

			@Override
			public String getDescription() {
				return "Shuts the system down!";
			}

			@Override
			public boolean onExecute(String[] args) {
				return true;
			}
			
		});
		
		mainLogic();

		Instance.onUnload();

		System.out.println(PluginTools.ANSI_RESET);
	}

	private static void mainLogic() {

		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		while (running) {
			try {
				System.out.print(PluginTools.ANSI_RESET + "$ ");
				String in = keyboard.readLine();
				
				if(in.equalsIgnoreCase("exit") || in.equalsIgnoreCase("/exit"))
					running = false;
				else
				{
					if(!in.trim().isEmpty()){
						String[] args = in.split(" ");
						eventHandler.onCommand(args);
					}
				}
				
			} catch (Exception e) {
				System.out.println(PluginTools.ANSI_RED + "An Error occourd!");
				e.printStackTrace();
			}

		}

	}
}
