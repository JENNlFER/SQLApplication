package jteissler.csci1320.simplesql;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * A static utility class for logging buffered string to output files and
 * to the terminal. <br>
 *
 * This class will automatically open writers for log files upon the first
 * logging instance, and automatically close them when the program is
 * terminated with any exit code.
 *
 * @author J Teissler
 * @date 2/5/17
 */
public class Log
{
	/** Bold name of the project. */
	private static final String PROJECT_NAME = "\u001B[1m[SimpleSQL]\u001B[0m ";
	/** Yellow error code text color */
	private static final String ERROR_CODE = "\u001B[33m";
	/** Code to reset all text formatting */
	private static final String RESET_CODE = "\u001B[0m";
	/** Styles the error cursor */
	private static final String CURSOR_CODE = "\u001B[1m\u001B[5m";
	/** Ends the style of the error cursor */
	private static final String CURSOR_CODE_END = "\u001B[22m";
	/** The relative path of the status log */
	private static final String STATUS_PATH = "status.txt";
	/** The relative path of the error log */
	private static final String ERROR_PATH = "error.txt";
	/** The system-specific newline character */
	private static final String NEWLINE = System.lineSeparator();

	/** Buffered status log writer */
	private static PrintWriter statusWriter;
	/** Buffered error log writer */
	private static PrintWriter errorWriter;

	/** Static code which runs upon the first use of this class */
	static
	{
		try
		{
			// Open the writers and leave them open.
			statusWriter = new PrintWriter(new BufferedWriter(new FileWriter(STATUS_PATH, true)));
			errorWriter = new PrintWriter(new BufferedWriter(new FileWriter(ERROR_PATH, true)));

			// Register a shutdown hook so the writers release file locks upon program end.
			Runtime.getRuntime().addShutdownHook(new Thread(() ->
			{
				statusWriter.close();
				errorWriter.close();
			}));
		}
		catch (IOException e)
		{
			log(e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Log an SQL status message to file and console.
	 *
	 * @param command Parsed SQL arguments.
	 * @param status The status message.
	 */
	public static void status(List<String> command, String status)
	{
		String out = status + NEWLINE + "(" + String.join(" ", command) + ")";
		statusWriter.println(out + NEWLINE);
		log(out);
	}

	/**
	 * Log an SQL error message to file and console.
	 *
	 * @param error The error message.
	 */
	public static void error(String error)
	{
		errorWriter.println(error + NEWLINE);
		log(ERROR_CODE + error + RESET_CODE);
	}

	/**
	 * Logs an SQL error message to file and console.
	 *
	 * @param command Parsed SQL arguments.
	 * @param error The error message.
	 */
	public static void error(List<String> command, String error)
	{
		String out = error + NEWLINE + "(" + String.join(" ", command) + ")";
		errorWriter.println(out + NEWLINE);
		log(ERROR_CODE + out + RESET_CODE);
	}

	/**
	 * Logs an SQL error message to file and console, along with
	 * an index which points to which SQL argument caused the issue.
	 *
	 * @param command Parsed SQL arguments.
	 * @param index The index of the cause of the error.
	 * @param error The error message.
	 */
	public static void error(List<String> command, int index, String error)
	{
		String indexString = "";
		for (int i = 0; i <= index; i++)
		{
			indexString += command.get(i) + " ";
		}

		indexString = indexString.replaceAll(".", " ") + "^";

		String out = error + NEWLINE + ERROR_CODE + "(" + String.join(" ", command) + ")" + NEWLINE + indexString;
		errorWriter.println(out + NEWLINE);
		log(ERROR_CODE + out + RESET_CODE);
	}

	public static void log(String log)
	{
		System.out.println(PROJECT_NAME + log.replaceAll(NEWLINE, NEWLINE + RESET_CODE + PROJECT_NAME)
				.replaceAll("\\^", CURSOR_CODE + "^" + CURSOR_CODE_END) + NEWLINE);
	}
}
