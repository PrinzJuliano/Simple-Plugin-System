package de.pjog.plugin;

/**
 * A simple guideline for making Commands.
 * @author PrinzJuliano
 *
 */
public interface Command {
	
	/**
	 * All Permissions the command can use.
	 * @return the permissions
	 */
	public String[] getPermissions();
	
	/**
	 * The basic Syntax e.g. /help
	 * @return the syntax
	 */
	public String getSyntax();
	
	/**
	 * The name of the Command.
	 * @return the name
	 */
	public String getName();
	
	/**
	 * All aliases the Command could accept
	 * @return the aliases
	 */
	public String[] getAliases();
	
	/**
	 * What the Command does.
	 * @return the description
	 */
	public String getDescription();

	/**
	 * What happens when the command is called.
	 * @param args All arguments including the command name.
	 * @return Success?
	 */
	public boolean onExecute(String[] args);
}
