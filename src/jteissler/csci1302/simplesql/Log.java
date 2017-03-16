package jteissler.csci1302.simplesql;

import com.sun.org.apache.bcel.internal.generic.NEW;
import jteissler.csci1302.sqlgui.WorkbenchOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;

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
	/** System-dependant newline character */
	private static final String NEWLINE = System.lineSeparator();

	/** Function to handle logging status messages */
	private static BiConsumer<List<String>, String> statusWriter;

	/** Function to handle logging error messages */
	private static BiConsumer<List<String>, String> errorWriter;


	/** Buffered status log writer */
	private static PrintWriter statusFileWriter;
	/** Buffered error log writer */
	private static PrintWriter errorFileWriter;

	/** Static code which runs upon the first use of this class */
	static
	{
		try
		{
			// Open the writers and leave them open.
			statusFileWriter = new PrintWriter(new BufferedWriter(new FileWriter("status.log", true)));
			errorFileWriter = new PrintWriter(new BufferedWriter(new FileWriter("error.log", true)));

			// Register a shutdown hook so the writers release file locks upon program end.
			Runtime.getRuntime().addShutdownHook(new Thread(() ->
			{
				statusFileWriter.close();
				errorFileWriter.close();
			}));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Set the function to handle logging status messages.
	 */
	public static void setStatusWriter(BiConsumer<List<String>, String> consumer)
	{
		statusWriter = consumer;
	}

	/**
	 * Set the function to handle logging error messages.
	 */
	public static void setErrorWriter(BiConsumer<List<String>, String> consumer)
	{
		errorWriter = consumer;
	}


	/**
	 * Log an SQL status message.
	 *
	 * @param command Parsed SQL arguments.
	 * @param status The status message.
	 */
	public static void status(List<String> command, String status)
	{
		String out = status + NEWLINE + "(" + String.join(" ", command) + ")";
		statusWriter.accept(command, out);

		if (WorkbenchOptions.USE_STATUS_LOG)
		{
			statusFileWriter.println(out + NEWLINE);
		}
	}

	/**
	 * Logs an SQL error message.
	 *
	 * @param command Parsed SQL arguments.
	 * @param error The error message.
	 */
	public static void error(List<String> command, String error)
	{
		String out = error + NEWLINE + "(" + String.join(" ", command) + ")";
		errorWriter.accept(command, out);

		if (WorkbenchOptions.USE_ERROR_LOG)
		{
			errorFileWriter.println(out + NEWLINE);
		}
	}

	/**
	 * Logs an SQL error message, along with
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

		String out = error + NEWLINE + "(" + String.join(" ", command) + ")" + NEWLINE + indexString;
		errorWriter.accept(command, out);

		if (WorkbenchOptions.USE_ERROR_LOG)
		{
			errorFileWriter.println(out + NEWLINE);
		}
	}
}
