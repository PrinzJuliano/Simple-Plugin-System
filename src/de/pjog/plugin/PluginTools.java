package de.pjog.plugin;

import java.util.logging.Level;

/**
 * A Utility class for Plugins and Console Interaction.
 * @author PrinzJuliano
 *
 */
public class PluginTools {
	
	//Colors
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[90m";
	public static final String ANSI_RED = "\u001B[91m";
	public static final String ANSI_GREEN = "\u001B[92m";
	public static final String ANSI_YELLOW = "\u001B[93m";
	public static final String ANSI_BLUE = "\u001B[94m";
	public static final String ANSI_PURPLE = "\u001B[95m";
	public static final String ANSI_CYAN = "\u001B[96m";
	public static final String ANSI_WHITE = "\u001B[97m";
	public static final String ANSI_BOLD = "\u001B[1";
	public static final String ANSI_ITALIC = "\u001B[3";
	public static final String ANSI_UNDERLINE = "\u001B[1";
	
	/**
	 * Nothing happens here
	 */
	public PluginTools(){
	}
	
	/**
	 * Default Name for the Logger. (Should be replaced when implementing the Plugin interface)
	 * @return the name
	 */
	public String getName(){
		return "Default";
	}
	
	/**
	 * Write something out in the console.
	 * @param msg The Message
	 */
	protected void log(Object msg)
	{
		log(msg, false);
	}
	
	/**
	 * Write something out in the console with the prefix INFORMATION
	 * @param msg the msg
	 * @param show_Level should the prefix be shown?
	 */
	protected void log(Object msg, boolean show_Level)
	{
		if(show_Level){
			log(Level.INFO, ANSI_RESET+ANSI_CYAN+"["+getName()+"]: "+ANSI_WHITE+msg.toString());
		}
		else {
			System.out.println(ANSI_RESET+ANSI_CYAN+"["+getName()+"]: "+ANSI_WHITE+msg.toString());
		}
	}
	
	/**
	 * Write something out to the console with any Level
	 * @param level the level of info
	 * @param msg the message
	 */
	protected void log(Level level, Object msg)
	{
		System.out.println(level.toString() + " " + msg.toString());
	}
	
}
