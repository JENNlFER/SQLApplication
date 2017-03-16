package jteissler.csci1302.sqlgui;

/**
 * @author J Teissler
 * @date 3/15/17
 */
public class WorkbenchOptions
{
	public static boolean EXECUTE_HIGHLIGHTED = true;

	public static boolean USE_STATUS_LOG = false;
	public static boolean USE_ERROR_LOG = false;

	public static String MASTER_DIRECTORY = "sql_data";

	public static String SAVE_FILE_EXTENSION = "sql";
	public static String TABLE_FILE_EXTENSION = "tab";

	public static CommandDivider COMMAND_DIVIDER = CommandDivider.SEMICOLON;

	public enum CommandDivider
	{
		SEMICOLON, EACH_LINE, EMPTY_LINE
	}

}
