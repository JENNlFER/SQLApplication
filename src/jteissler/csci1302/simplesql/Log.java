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
	private static BiConsumer<String, String> statusWriter;

	/** Function to handle logging error messages */
	private static BiConsumer<String, String> errorWriter;


	/** Buffered status log writer */
	private static PrintWriter statusFileWriter;
	/** Buffered error log writer */
	private static PrintWriter errorFileWriter;


	/**
	 * Set the function to handle logging status messages.
	 */
	public static void setStatusWriter(BiConsumer<String, String> consumer)
	{
		statusWriter = consumer;
	}

	/**
	 * Set the function to handle logging error messages.
	 */
	public static void setErrorWriter(BiConsumer<String, String> consumer)
	{
		errorWriter = consumer;
	}


	public static void status(List<String> command, String status, List<Pair<Long, String>> rows)
	{
		StringBuilder build = new StringBuilder();

		for (Pair<Long, String> row : rows)
		{
			build.append(row.getRight());
			build.append(NEWLINE);
		}

		build.append(NEWLINE);
		build.append(status);

		statusWriter.accept(String.join(" ", command), build.toString());


		// Receive and format the selected rows.
		StringBuilder dataBuilder = new StringBuilder();
		for (Pair<Long, String> row : rows)
		{
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < 4 - (""+row.getLeft()).length(); i++)
			{
				dataBuilder.append(" ");
			}

			dataBuilder.append(row.getLeft());
			dataBuilder.append(" - ");
			dataBuilder.append(row.getRight());
			dataBuilder.append(NEWLINE);
		}

		if (WorkbenchOptions.doStatusLog())
		{
			setUpStatusLog();
			statusFileWriter.println("(" + String.join(" ", command) + ")");
			statusFileWriter.print(dataBuilder.toString());
			statusFileWriter.println(status);
			statusFileWriter.println();
			statusFileWriter.flush();
		}
	}

	/**
	 * Log an SQL status message.
	 *
	 * @param command Parsed SQL arguments.
	 * @param status The status message.
	 */
	public static void status(List<String> command, String status)
	{
		statusWriter.accept(String.join(" ", command), status);

		if (WorkbenchOptions.doStatusLog())
		{
			setUpStatusLog();
			statusFileWriter.println("(" + String.join(" ", command) + ")");
			statusFileWriter.println(status);
			statusFileWriter.println();
			statusFileWriter.flush();
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
		errorWriter.accept(String.join(" ", command), error);

		if (WorkbenchOptions.doErrorLog())
		{
			setUpErrorLog();
			errorFileWriter.println(error);
			errorFileWriter.println("(" + String.join(" ", command) + ")");
			errorFileWriter.println();
			errorFileWriter.flush();
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
		errorWriter.accept(String.join(" ", command), out);

		if (WorkbenchOptions.doErrorLog())
		{
			setUpErrorLog();
			errorFileWriter.println(out);
			errorFileWriter.println();
			errorFileWriter.flush();
		}
	}

	private static void setUpStatusLog()
	{
		if (statusFileWriter == null)
		{
			try
			{
				// Open the writers and leave them open.
				statusFileWriter = new PrintWriter(new BufferedWriter(new FileWriter("status.log", true)));

				// Register a shutdown hook so the writers release file locks upon program end.
				Runtime.getRuntime().addShutdownHook(new Thread(() ->
				{
					statusFileWriter.close();
				}));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static void setUpErrorLog()
	{
		if (errorFileWriter == null)
		{
			try
			{
				// Open the writers and leave them open.
				errorFileWriter = new PrintWriter(new BufferedWriter(new FileWriter("error.log", true)));

				// Register a shutdown hook so the writers release file locks upon program end.
				Runtime.getRuntime().addShutdownHook(new Thread(() ->
				{
					errorFileWriter.close();
				}));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
