package jteissler.csci1302.simplesql;

import jteissler.csci1302.sqlgui.WorkbenchOptions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A database implementation which creates databases as directories and
 * individual tables as files within those directories.
 *
 * @see StringDatabase for documentation on the overridden methods.
 *
 * @author J Teissler
 * @date 2/5/17
 */
public class SQLDatabase implements StringDatabase
{
	@Override
	public void createDatabase(String database) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		File file = new File(WorkbenchOptions.getMasterFile() + "/" + database);

		// Check if the database already exists
		if (!file.exists() || file.isFile())
		{
			// If not, make the database.
			if (file.mkdir())
			{
				return;
			}

			throw new SQLException("Database \"" + database + "\" was unable to be created for unknown reasons.");
		}

		throw new SQLException("Database \"" +database + "\" was not created because it already exists.");
	}

	@Override
	public void dropDatabase(String database) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		Path directory = Paths.get(WorkbenchOptions.getMasterFile() + "/" + database);
		try
		{
			// Iterate over the directory structure and delete the database along with all sub-directories and files.
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>()
			{
				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException
				{
					Files.delete(path);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path path, IOException exception) throws IOException
				{
					Files.delete(path);
					return FileVisitResult.CONTINUE;
				}
			});
		}
		catch (IOException e)
		{
			throw new SQLException("Database \"" + database + "\" was unable to be deleted: " + e.getMessage() + ".");
		}

		if (directory.toFile().exists())
		{
			throw new SQLException("Database \"" + database + "\" was unable to be deleted for unknown reasons.");
		}
	}

	@Override
	public void createTable(String database, String table) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		File directory = new File(WorkbenchOptions.getMasterFile() + "/" + database);

		// Ensure the database exists to create the table in.
		if (directory.exists())
		{
			File file = new File(WorkbenchOptions.getMasterFile() + "/" + database + "/" + table + "." + WorkbenchOptions.getTableExtension());

			// Check if the table already exists.
			if (!file.exists())
			{
				try
				{
					// If not, create the table.
					if (file.createNewFile())
					{
						return;
					}

					throw new SQLException("Table \"" + table + "\" was unable to be created for unknown reasons.");
				}
				catch (IOException e)
				{
					throw new SQLException("Table \"" + table + "\" was unable to be created: " + e.getMessage() + ".");
				}
			}

			throw new SQLException("Table \"" + table + "\" was unable to be created because it already exists.");
		}

		throw new SQLException("Table \"" + table + "\" was unable to created due to database \"" + database + "\" not existing.");
	}

	@Override
	public void dropTable(String database, String table) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		File directory = new File(WorkbenchOptions.getMasterFile() + "/" + database);

		// Ensure the database exists to drop the table from.
		if (directory.exists())
		{
			File file = new File(WorkbenchOptions.getMasterFile() + "/" + database + "/" + table + "." + WorkbenchOptions.getTableExtension());

			// Check if the table currently exists.
			if (file.exists())
			{
				// If so, check if it can be deleted.
				if (file.canWrite())
				{
					// If so, delete it.
					if (file.delete())
					{
						return;
					}

					throw new SQLException("Table \"" + table + "\" was unable to be deleted for unknown reasons.");
				}

				throw new SQLException("Table \"" + table + "\" was unable to be dropped due to insufficient write privileges.");
			}

			throw new SQLException("Table \"" + table + "\" was unable to be dropped because it does not exist.");
		}

		throw new SQLException("Table \"" + table + "\" was unable to dropped due to database \"" + database + "\" not existing.");
	}

	@Override
	public void insert(String database, String table, String value) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		File directory = new File(WorkbenchOptions.getMasterFile() + "/" + database);

		// Ensure the database exists.
		if (directory.exists())
		{
			File file = new File(WorkbenchOptions.getMasterFile() + "/" + database + "/" + table + "." + WorkbenchOptions.getTableExtension());

			// Ensure the table exists.
			if (file.exists())
			{
				// Check if the table can be written to.
				if (file.canWrite())
				{
					// If so, write to the table.
					PrintWriter writer = null;

					try
					{
						writer = new PrintWriter(new FileWriter(file, true));
						writer.println(value);
					}
					catch (IOException e)
					{
						throw new SQLException("Value \"" + value + "\" was unable to be inserted: " + e.getMessage() + ".");
					}
					finally
					{
						// Make sure the writer closes and gives up the write lock.
						if (writer != null)
						{
							writer.close();
						}
					}

					return;
				}

				throw new SQLException("Value \"" + value + "\" was unable to be inserted due to insufficient write privileges.");
			}

			throw new SQLException("Value \"" + value + "\" was unable to be inserted due to table \"" + table + "\" not existing.");
		}

		throw new SQLException("Value \"" + value + "\" was unable to be inserted due to database \"" + database + "\" not existing.");
	}

	@Override
	public long update(String database, String table, String value, String search) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		File directory = new File(WorkbenchOptions.getMasterFile() + "/" + database);

		// Ensure the database exists.
		if (directory.exists())
		{
			File file = new File(WorkbenchOptions.getMasterFile() + "/" + database + "/" + table + "." + WorkbenchOptions.getTableExtension());

			// Ensure the table exists.
			if (file.exists())
			{
				// Check if the table can be read from and written to.
				if (file.canRead() && file.canWrite())
				{
					// If so, perform the update operation.
					long count = 0;
					Scanner reader = null;
					FileWriter writer = null;

					try
					{
						String line;
						String lines = "";
						reader = new Scanner(new FileReader(file));

						// Iterate through the table and apply changes.
						while (reader.hasNext())
						{
							line = reader.nextLine();
							if (line.equals(search))
							{
								count++;
								line = value;
							}

							lines += line + System.lineSeparator();
						}

						// Close the reader and open a writer to write the changes to the table.
						reader.close();
						writer = new FileWriter(file);
						writer.write(lines);

						return count;
					}
					catch (IOException e)
					{
						throw new SQLException("Value \"" + search + "\" was unable to be updated to value \"" + value + "\": " + e.getMessage() + ".");
					}
					finally
					{
						//  Make sure the reader closes.
						if (reader != null)
						{
							reader.close();
						}

						// Make sure the writer closes and gives up the write lock.
						if (writer != null)
						{
							try
							{
								writer.close();
							}
							catch (IOException e)
							{
								// If the close method fails, there's nothing we can do (sans a loop) to fix the problem.
								throw new SQLException("Value \"" + search + "\" was unable to be updated to value \"" + value + "\": " + e.getMessage() + ".");
							}
						}
					}
				}

				throw new SQLException("Value \"" + search + "\" was unable to be updated to value \"" + value + "\" due to insufficient read/write privileges.");
			}

			throw new SQLException("Value \"" + search + "\" was unable to be updated to value \"" + value + "\" due to table \"" + table + "\" not existing.");
		}

		throw new SQLException("Value \"" + search + "\" was unable to be updated to value \"" + value + "\" due to database \"" + database + "\" not existing.");
	}

	@Override
	public long delete(String database, String table, String search) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		File directory = new File(WorkbenchOptions.getMasterFile() + "/" + database);
		String nullFix = search == null ? "Values were" : "Value \"" + search + "\" was";

		// Ensure the database exists.
		if (directory.exists())
		{
			File file = new File(WorkbenchOptions.getMasterFile() + "/" + database + "/" + table + "." + WorkbenchOptions.getTableExtension());

			// Ensure the table exists.
			if (file.exists())
			{
				// Check if the table can be read from and written to.
				if (file.canRead() && file.canWrite())
				{
					// If so, perform the delete command.
					long count = 0;
					Scanner reader = null;
					FileWriter writer = null;

					try
					{
						String line;
						String lines = "";
						reader = new Scanner(new FileReader(file));

						// Iterate through the table and delete lines.
						while (reader.hasNext())
						{
							line = reader.nextLine();
							if (search == null || line.equals(search))
							{
								count++;
								continue;
							}

							lines += line + System.lineSeparator();
						}

						// Close the reader and open a writer to write the changes to file.
						reader.close();
						writer = new FileWriter(file);
						writer.write(lines);

						return count;
					}
					catch (IOException e)
					{
						throw new SQLException(nullFix + " unable to be deleted from table \"" + table + "\": " + e.getMessage() + ".");
					}
					finally
					{
						// Make sure the reader closes.
						if (reader != null)
						{
							reader.close();
						}

						// Make sure the writer closes and gives up the write lock.
						if (writer != null)
						{
							try
							{
								writer.close();
							}
							catch (IOException e)
							{
								// If the close method fails, there's nothing we can do (sans a loop) to fix the problem.
								throw new SQLException(nullFix + " unable to be deleted from table \"" + table + "\": " + e.getMessage() + ".");
							}
						}
					}
				}

				throw new SQLException(nullFix + " unable to be deleted from table \"" + table + "\" due to insufficient read/write privileges.");
			}

			throw new SQLException(nullFix + " unable to be deleted due to table \"" + table + "\" not existing.");
		}

		throw new SQLException(nullFix + " unable to be deleted due to database \"" + database + "\" not existing.");
	}

	@Override
	public List<Pair<Long, String>> select(String database, String table, String search) throws SQLException
	{
		File master = new File(WorkbenchOptions.getMasterFile());
		if (!master.exists()) master.mkdir();

		File directory = new File(WorkbenchOptions.getMasterFile() + "/" + database);
		String nullFix = search == null ? "Values were" : "Value \"" + search + "\" was";

		// Ensure the database exists.
		if (directory.exists())
		{
			File file = new File(WorkbenchOptions.getMasterFile() + "/" + database + "/" + table + "." + WorkbenchOptions.getTableExtension());

			// Ensure the file exists.
			if (file.exists())
			{
				// Check if the file can be read from.
				if (file.canRead())
				{
					// If so, perform the select command.
					Scanner reader = null;

					try
					{
						String line;
						List<Pair<Long, String>> lines = new ArrayList<>();
						reader = new Scanner(new FileReader(file));

						// Iterate over the table and store the relevant lines.
						long count = 0;
						while (reader.hasNext())
						{
							line = reader.nextLine();
							count++;
							if (search == null || line.equals(search))
							{
								lines.add(Pair.of(count, line));
							}
						}
						return lines;
					}
					catch (IOException e)
					{
						throw new SQLException(nullFix + " unable to be selected: " + e.getMessage() + ".");
					}
					finally
					{
						// Make sure the reader closes.
						if (reader != null)
						{
							reader.close();
						}
					}
				}

				throw new SQLException(nullFix + " unable to be selected due to insufficient read privileges.");
			}

			throw new SQLException(nullFix + " unable to be selected due to table \"" + table + "\" not existing.");
		}

		throw new SQLException(nullFix + " unable to be selected due to database \"" + database + "\" not existing.");
	}
}
