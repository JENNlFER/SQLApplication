package jteissler.csci1302.sqlgui;

import java.util.prefs.Preferences;

/**
 * @author J Teissler
 * @date 3/15/17
 */
public class WorkbenchOptions
{
	private static Preferences preferences;

	//the enum that contains all of the command dividers.
	// in our case we have options for semicolon, each line, and an empty line.
	/*
	i.e
	semicolon :

	CREATE DATABASE yeah;
	DROP DATABASE yeah;

	each line :

	CREATE DATABASE yeah
	DROP DATABASE yeah

	empty line (THE EMPTY LINE BETWEEN THE TWO COMMANDS IS REQUIRED!):

	CREATE
	DATABASE yeah

	DROP
	DATABASE
	yeah

	 */
	public enum CommandDivider
	{
		SEMICOLON, EACH_LINE, EMPTY_LINE
	}

	public WorkbenchOptions()
	{
		preferences = Preferences.userRoot().node(getClass().getName());
	}

	public static boolean doHighlightedCommands()
	{
		return preferences.getBoolean("highlighted", true);
	}

	public static void setHighlightedCommands(boolean value)
	{
		preferences.putBoolean("highlighted", value);
	}

	public static boolean doStatusLog()
	{
		return preferences.getBoolean("status", false);
	}

	public static void setStatusLog(boolean value)
	{
		preferences.putBoolean("status", value);
	}

	public static boolean doErrorLog()
	{
		return preferences.getBoolean("error", false);
	}

	public static void setErrorLog(boolean value)
	{
		preferences.putBoolean("error", value);
	}

	public static String getMasterFile()
	{
		return preferences.get("master", "sql_data");
	}

	public static void setMasterFile(String value)
	{
		preferences.put("master", value);
	}

	public static String getSaveExtension()
	{
		return preferences.get("save", "sql");
	}

	public static void setSaveExtension(String value)
	{
		preferences.put("save", value);
	}

	public static String getTableExtension()
	{
		return preferences.get("table", "tab");
	}

	public static void setTableExtension(String value)
	{
		preferences.put("table", value);
	}

	public static CommandDivider getCommandDivider()
	{
		return CommandDivider.valueOf(preferences.get("divider", CommandDivider.SEMICOLON.name()));
	}

	public static void setCommandDivider(CommandDivider value)
	{
		preferences.put("divider", value.name());
	}
}
