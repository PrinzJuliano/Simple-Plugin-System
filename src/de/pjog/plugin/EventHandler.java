package de.pjog.plugin;

import java.util.List;
import java.util.ArrayList;

/**
 * Handles all the Events (currently only Commands)
 * @author PrinzJuliano
 */
public class EventHandler {
	
	private static List<Command> commands;
	private static List<String> commandNames;
	
	/**
	 * Instantiates the commands Array and registers the /help command.
	 */
	public EventHandler(){
		commands = new ArrayList<Command>();
		commandNames = new ArrayList<String>();
		
		register(new HelpCommand(this));
	}
	
	/**
	 * Get all the commands. (Needed for /help)
	 * @return all the commands
	 */
	public List<Command> getCommands(){
		return commands;
	}
	
	/**
	 * Register a command
	 * @param cmd The Command
	 * @return true if the command was registered or not
	 */
	public static boolean register(Command cmd){
		boolean cont = false;
		List<String> wrongAliases = new ArrayList<String>();
		if(cmd.getAliases() != null){
			for(String s : cmd.getAliases()){
				cont = cont || commandNames.contains(s);
				if(commandNames.contains(s))
				{
					wrongAliases.add(s);
				}
				
			}
		}
		
		
		if(!commandNames.contains(cmd.getName())){
			commands.add(cmd);
			commandNames.add(cmd.getName());
			System.out.println(PluginTools.ANSI_RESET + PluginTools.ANSI_YELLOW + "[EventHandler]: " + PluginTools.ANSI_GREEN + "Successfully registered: " + cmd.getName());
			
			if(cont)
			{
				String all = "";
				for(String s : wrongAliases)
				{
					all += s + " ";
				}
				System.out.println(PluginTools.ANSI_RESET + PluginTools.ANSI_YELLOW + "[EventHandler]: " + PluginTools.ANSI_RED + "These Aliases are not working: " + all.trim());
			}
			if(cmd.getAliases() != null){
				for(String alias : cmd.getAliases())
				{
					if(!(wrongAliases.contains(alias) || commandNames.contains(alias))){
						commandNames.add(alias);
					}
				}
			}
			return true;
		}
		else
		{
			System.out.println(PluginTools.ANSI_RESET + PluginTools.ANSI_YELLOW + "[EventHandler]: " + PluginTools.ANSI_RED + "Failed to register: " + cmd.getName());
			return false;
		}
	}
	
	/**
	 * Unregisters a command.
	 * @param cmd The Command
	 * @return if the command could be unregistered or not
	 */
	public static boolean unregister(Command cmd)
	{
		if(commands.contains(cmd))
		{
			commands.remove(cmd);
			commandNames.remove(cmd.getName());
			return true;
		}
		return false;
	}
	
	/**
	 * Execute the according command to args[0].
	 * @param args The command parameters.
	 */
	public void onCommand(String[] args){
		String command = "> ";
		for(String s : args)
			command += s + " ";
		System.out.println(PluginTools.ANSI_WHITE +command.trim());
		for(Command c : commands){
			if(args[0].equalsIgnoreCase(c.getName()) || args[0].equalsIgnoreCase("/" + c.getName()))
			{
				c.onExecute(args);
				return;
			}
			else
			{
				if(c.getAliases() != null){
					for(String alias : c.getAliases())
					{
						if(args[0].equalsIgnoreCase(alias) || args[0].equalsIgnoreCase("/" + alias))
						{
							c.onExecute(args);
							return;
						}
					}
				}
			}
		}
		System.out.println(PluginTools.ANSI_RESET + PluginTools.ANSI_RED + "There is no command called " + args[0] + ".");
	}

}

/**
 * Help Command.
 * Shows all the Commands.
 * @author PrinzJuliano
 *
 */
class HelpCommand extends PluginTools implements Command {

	private EventHandler Instance;
	
	/**
	 * Make the EventHandler part of this.
	 * @param inst The Instance
	 */
	public HelpCommand(EventHandler inst)
	{
		Instance = inst;
	}
	
	@Override
	public String getName(){
		return "help";
	}
	
	@Override
	public String[] getPermissions() {
		return null;
	}

	@Override
	public String getSyntax() {
		return "/help";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Lists all Commands";
	}

	@Override
	public boolean onExecute(String[] args) {
		if(args.length > 1)
		{
			int page = 0;
			try{
				page = Integer.parseInt(args[1]);
				 
				int pages = Instance.getCommands().size() / 5 + 1;
				if(page <= 0 || page > pages){
					log(ANSI_RED + "Illigal number");
					return false;
				}
				
				System.out.println(ANSI_GREEN + "=====[HELP "+page+" / " + pages + "]=====");
				
				
				for(int i = (page-1)*5; i < 5+(page-1)*5; i++)
				{
					if(!(i >= Instance.getCommands().size())){
						Command c = Instance.getCommands().get(i);
						System.out.println(ANSI_GREEN + c.getSyntax() + " - " + c.getDescription());
					}
				}
				
			}catch(Exception e)
			{
				String command = args[1];
				for(Command c : Instance.getCommands())
				{
					boolean aliasEquals = false;
					if(c.getAliases() != null){
						for(String alias : c.getAliases()){
							if(command.equalsIgnoreCase(alias))
							{
								aliasEquals = true;
								break;
							}
						}
					}
					if(command.equalsIgnoreCase(c.getName()) || aliasEquals)
					{
						System.out.println(ANSI_GREEN + c.getSyntax() + " - " + c.getDescription());
						return true;
					}
				}
				System.out.println(PluginTools.ANSI_RESET + PluginTools.ANSI_RED + "There is no command called " + args[1] + ".");
			}
		}
		else
		{
			onExecute(new String[]{"help", "1"});
		}
		return true;
	}
	
}
