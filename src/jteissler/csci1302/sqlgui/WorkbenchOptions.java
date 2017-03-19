package jteissler.csci1302.sqlgui;

import java.util.prefs.Preferences;

/**
 * Static preferences utility which stores preferences persistently in some backing store.
 * If no backing store is able to be created, the preferences will still function, albeit
 * in a non-persistent manner. <br>
 *
 * Note that while this class is used in a static manner, a single instance of it must
 * be instantiated before any static methods can be used.
 *
 * @author J Teissler & Kyle Turner
 *         Created on 3/15/17
 */
public class WorkbenchOptions
{
	/** The backing store */
	private static Preferences preferences;

	/**
	 * Enum specifying the various type of command divider options.
	 */
	public enum CommandDivider
	{
		SEMICOLON, EACH_LINE, EMPTY_LINE
	}

	/**
	 * Instantiates the backing store
	 */
	public WorkbenchOptions()
	{
		preferences = Preferences.userRoot().node(getClass().getName());
	}

	/**
	 * @return Whether to execute highlighted commands.
	 */
	public static boolean doHighlightedCommands()
	{
		return preferences.getBoolean("highlighted", true);
	}

	/**
	 * Sets whether to execute highlighted commands.
	 */
	public static void setHighlightedCommands(boolean value)
	{
		preferences.putBoolean("highlighted", value);
	}

	/**
	 * @return Whether to output to a status log.
	 */
	public static boolean doStatusLog()
	{
		return preferences.getBoolean("status", false);
	}

	/**
	 * Sets whether to output to a status log.
	 */
	public static void setStatusLog(boolean value)
	{
		preferences.putBoolean("status", value);
	}

	/**
	 * @return Whether to output to an error log.
	 */
	public static boolean doErrorLog()
	{
		return preferences.getBoolean("error", false);
	}

	/**
	 * Sets whether to output to an error log.
	 */
	public static void setErrorLog(boolean value)
	{
		preferences.putBoolean("error", value);
	}

	/**
	 * @return The name of the master file directory to use.
	 */
	public static String getMasterFile()
	{
		return preferences.get("master", "sql_data");
	}

	/**
	 * Sets the name of the master file directory.
	 */
	public static void setMasterFile(String value)
	{
		preferences.put("master", value);
	}

	/**
	 * @return The save file extension to use.
	 */
	public static String getSaveExtension()
	{
		return preferences.get("save", "sql");
	}

	/**
	 * Sets the save file extension.
	 */
	public static void setSaveExtension(String value)
	{
		preferences.put("save", value);
	}

	/**
	 * @return The table file extension to use.
	 */
	public static String getTableExtension()
	{
		return preferences.get("table", "tab");
	}

	/**
	 * Sets the table file extension.
	 */
	public static void setTableExtension(String value)
	{
		preferences.put("table", value);
	}

	/**
	 * @return The command dividing method to use.
	 */
	public static CommandDivider getCommandDivider()
	{
		return CommandDivider.valueOf(preferences.get("divider", CommandDivider.SEMICOLON.name()));
	}

	/**
	 * Sets the method by which commands are divided.
	 */
	public static void setCommandDivider(CommandDivider value)
	{
		preferences.put("divider", value.name());
	}
}
