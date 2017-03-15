package jteissler.csci1320.simplesql;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The main class of the SQL system. <br>
 *
 * This class will attempt to first read a list of commands from a text file.
 * If the file exists, any contained commands will be executed. If the file
 * does not exist or once the commands have finished executing, the program
 * will enter "terminal input mode" which allows SQL commands to be entered
 * directly into the terminal prompt. Typing "EXIT" will leave this mode. <br>
 *
 * All successful SQL actions are logged to "status.txt".
 * All invalid commands or times at which a valid command encountered
 * an exception while executed are logged to "error.txt".
 *
 * @author J Teissler
 * @date 2/6/17
 */
public class Main
{
	/** The command file which the program will read from on startup */
	private static final String COMMANDS = "DBUSER.txt";

	// Main loop
	public static void main(String[] args)
	{
		// Create the parser and pass it in an instance of a database.
		Parser parser = new AssignmentParser(new SQLDatabase());
		Scanner reader = null;

		try
		{
			// Attempt to locate the command file.
			File file = new File(COMMANDS);

			if (file.exists())
			{
				Log.log("Command file \"" + COMMANDS + "\" found!");

				// If the command file can be read, read it and execute commands.
				if (file.canRead())
				{
					reader = new Scanner(new FileReader(file));

					while (reader.hasNext())
					{
						// Parse an SQL command and run it if it is valid.
						parser.parse(reader.nextLine());

					}
				}
				else
				{
					Log.error("Program does not have read permissions for " + COMMANDS + ".");
				}
			}
			else
			{
				Log.error("Command file \"" + COMMANDS + "\" does not exist.");
			}
		}
		catch (IOException e)
		{
			Log.error("Error reading commands file: " + e.getMessage() + ".");
		}
		finally
		{
			// Make sure the reader closes.
			if (reader != null)
			{
				reader.close();
			}
		}

		// Create a scanner to get input from the terminal prompt.
		Scanner scan = new Scanner(System.in);
		String input;

		Log.log("ENTERING TERMINAL INPUT MODE. TYPE SQL COMMANDS OR TYPE \"EXIT\" TO QUIT.");

		// Wait for input from the user.
		while (true)
		{
			input = scan.nextLine();

			// Quit the program if "EXIT" is entered.
			if (input.equalsIgnoreCase("exit"))
			{
				break;
			}

			// PArse an SQL command and run it if it is valid.
			parser.parse(input);
		}
	}
}
