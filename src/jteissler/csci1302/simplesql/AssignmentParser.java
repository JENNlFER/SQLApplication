package jteissler.csci1302.simplesql;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input and determines what SQL command is being executed. <br>
 *
 * The {@link AssignmentParser#parse(String)} method also acts as an error
 * diagnosing method. If invalid syntax is entered into the parser, the
 * parser will do its best to determine the specific cause of the issue
 * and output information on the issue, as well as point it out in the logs
 * with a "^" character pointing to the suspected issue.
 *
 * @see Parser for documentation on overridden methods.
 *
 * @author J Teissler
 * @date 2/6/17
 */
public class AssignmentParser implements Parser
{
	// SQL Commands
	private static final String CREATE = "CREATE";
	private static final String TABLE = "TABLE";
	private static final String DATABASE = "DATABASE";
	private static final String DROP = "DROP";
	private static final String INSERT = "INSERT";
	private static final String SELECT = "SELECT";
	private static final String ASTERISK = "*";
	private static final String FROM = "FROM";
	private static final String DELETE = "DELETE";
	private static final String UPDATE = "UPDATE";
	private static final String WHERE = "WHERE";
	private static final String INTO = "INTO";
	private static final String EQUALS = "=";
	private static final String DOT = ".";

	/** REGEX pattern which matches the individual arguments in an SQL statement */
	private static final Pattern PATTERN = Pattern.compile("['“”\"].*?[\"“”']|[^\"“”'\\W]+|[*\\.=]");

	/** The SQL database instance */
	private final StringDatabase database;

	/**
	 * Create an assignment parser.
	 *
	 * @param database The database to send commands to.
	 */
	public AssignmentParser(StringDatabase database)
	{
		this.database = database;
	}

	@Override
	public void parse(String input)
	{
		// Clean up the input and separate out the individual arguments.

		String[] commands = input.split(";");

		for(String command : commands)
		{
			process(command);
		}
	}

	/**
	 * Alias for {@link Parser#parse(String)} but for multiple commands.
	 *
	 * @param input A list of SQL commands.
	 */
	@Override
	public void parse(List<String> input)
	{
		for(String command : input)
		{
			process(command);
		}
	}

	private void process(String input)
	{
		List<String> args = clean(input);

		if (args.size() >= 1)
		{
			// Check that an SQL command has been entered.
			switch (args.get(0).toUpperCase())
			{
				case CREATE: // Handle a CREATE command.
				{
					if (args.size() >= 2)
					{
						switch (args.get(1).toUpperCase())
						{
							case DATABASE: // Handle a CREATE DATABASE command.
							{
								if (args.size() >= 3)
								{
									// Validate the name of the database.
									String database = args.get(2);
									if (checkAlphanumeric(database))
									{
										try
										{
											// Send the CREATE DATABASE command to the StringDatabase.
											this.database.createDatabase(database);
											Log.status(args, "Database \"" + database + "\" was created.");
										}
										catch (SQLException e)
										{
											Log.error(args, e.getMessage());
										}
									}
									else
									{
										Log.error(args, 2, "The database name was not alphanumeric (A-Za-z0-9_-).");
									}
								}
								else
								{
									Log.error(args, 1, "No database name was specified.");
								}
								break;
							}
							case TABLE: // Handle a CREATE TABLE command.
							{
								if (args.size() >= 3)
								{
									// Validate the name of the database.
									String database = args.get(2);
									if (checkAlphanumeric(database))
									{
										if (args.size() >= 5)
										{
											String table = args.get(4);
											if (args.get(3).equals(DOT))
											{
												// Validate the name of the table.
												if (checkAlphanumeric(table))
												{
													try
													{
														// Send the CREATE TABLE command to the StringDatabase.
														this.database.createTable(database, table);
														Log.status(args, "Table \"" + table + "\" was created in database \"" + database + "\".");
													}
													catch (SQLException e)
													{
														Log.error(args, e.getMessage());
													}
												}
												else
												{
													Log.error(args, 4, "The table name was not alphanumeric (A-Za-z0-9_-).");
												}
											}
											else
											{
												Log.error(args, 3, "The table and database names were not separated by (\".\").");
											}
										}
										else
										{
											Log.error(args, 2, "No table name was specified.");
										}
									}
									else
									{
										Log.error(args, 2, "The database name was not alphanumeric (A-Za-z0-9_-).");
									}
								}
								else
								{
									Log.error(args, 1, "No database name was specified.");
								}
								break;
							}
							default:
							{
								Log.error(args, 1, "A non-table or non-database was attempted to be created.");
								break;
							}
						}
					}
					else
					{
						Log.error(args, 0, "No arguments were provided for the command.");
					}
					break;
				}
				case DROP: // Handle a DROP command.
				{
					if (args.size() >= 2)
					{
						switch (args.get(1).toUpperCase())
						{
							case DATABASE: // Handle a DROP DATABASE command.
							{
								if (args.size() >= 3)
								{
									// Validate the name of the database.
									String database = args.get(2);
									if (checkAlphanumeric(database))
									{
										try
										{
											// Send the DROP DATABASE command to the StringDatabase.
											this.database.dropDatabase(database);
											Log.status(args, "Database \"" + database + "\" was dropped.");
										}
										catch (SQLException e)
										{
											Log.error(args, e.getMessage());
										}
									}
									else
									{
										Log.error(args, 2, "The database name was not alphanumeric (A-Za-z0-9_-).");
									}
								}
								else
								{
									Log.error(args, 1, "No database name was specified.");
								}
								break;
							}
							case TABLE: // Handle a DROP TABLE command.
							{
								if (args.size() >= 3)
								{
									// Validate the name of the database.
									String database = args.get(2);
									if (checkAlphanumeric(database))
									{
										if (args.size() >= 5)
										{
											String table = args.get(4);
											if (args.get(3).equals(DOT))
											{
												// Validate the name of the table.
												if (checkAlphanumeric(table))
												{
													try
													{
														// Send the DROP TABLE command to the StringDatabase.
														this.database.dropTable(database, table);
														Log.status(args, "Table \"" + table + "\" was dropped from database \"" + database + "\".");
													}
													catch (SQLException e)
													{
														Log.error(args, e.getMessage());
													}
												}
												else
												{
													Log.error(args, 4, "The table name was not alphanumeric (A-Za-z0-9_-).");
												}
											}
											else
											{
												Log.error(args, 3, "The table and database names were not separated by (\".\").");
											}
										}
										else
										{
											Log.error(args, 2, "No table name was specified.");
										}
									}
									else
									{
										Log.error(args, 2, "The database name was not alphanumeric (A-Za-z0-9_-).");
									}
								}
								else
								{
									Log.error(args, 1, "No database name was specified.");
								}
								break;
							}
							default:
							{
								Log.error(args, 1, "A non-table or non-database was attempted to be dropped.");
								break;
							}
						}
					}
					else
					{
						Log.error(args, 0, "No arguments were provided for the command.");
					}
					break;
				}
				case SELECT: // Handle a SELECT command.
				{
					if (args.size() >= 3 && args.get(1).equals(ASTERISK) && args.get(2).equalsIgnoreCase(FROM))
					{
						if (args.size() >= 4)
						{
							// Validate the name of the databbase.
							String database = args.get(3);
							if (checkAlphanumeric(database))
							{
								if (args.size() >= 6)
								{
									String table = args.get(5);
									if (args.get(4).equals(DOT))
									{
										// Validate the name of the table.
										if (checkAlphanumeric(table))
										{
											if (args.size() == 6)
											{
												try
												{
													// Send the SELECT WHERE 1 command to the StringDatabase.
													List<Pair<Long, String>> rows = this.database.select(database, table, null);

													// Receive and format the selected rows.

													Log.status(args, rows.size() + " row(s) were selected from table \"" + table + "\" in database \"" + database + "\".", rows);
												}
												catch (SQLException e)
												{
													Log.error(args, e.getMessage());
												}
											}
											else
											{
												if (args.size() >= 7 && args.get(6).equalsIgnoreCase(WHERE))
												{
													if (args.size() >= 8)
													{
														// Begin the process of parsing out the search value.
														if (checkAlphanumeric(args.get(7)))
														{
															if (args.size() >= 9)
															{
																if (args.get(8).equalsIgnoreCase(EQUALS))
																{
																	if (args.size() >= 10)
																	{
																		String value = args.get(9);
																		try
																		{
																			// Send the SELECT WHERE VALUE = ? command to the StringDatabase.
																			List<Pair<Long, String>> rows = this.database.select(database, table, value);

																			Log.status(args, rows.size() + " row(s) matching value \"" + value + "\" were selected from table \"" + table + "\" in database \"" + database + "\".", rows);
																		}
																		catch (SQLException e)
																		{
																			Log.status(args, e.getMessage());
																		}
																	}
																	else
																	{
																		Log.error(args, 8, "No search value provided after equals sign.");
																	}
																}
																else
																{
																	Log.error(args, 8, "Column name was not denoted with an equals sign.");
																}
															}
															else
															{
																Log.error(args, 7, "No search value was given.");
															}
														}
														else
														{
															Log.error(args, 7, "Column name was not alphanumeric.");
														}
													}
													else
													{
														Log.error(args, 6, "Missing column name after \"WHERE\" command.");
													}
												}
												else
												{
													Log.error(args, 6, "An unrecognized command was entered.");
												}
											}
										}
										else
										{
											Log.error(args, 5, "The table name was not alphanumeric (A-Za-z0-9_-).");
										}
									}
									else
									{
										Log.error(args, 4, "The table and database names were not separated by (\".\").");
									}
								}
								else
								{
									Log.error(args, args.size() - 1, "No table name was specified.");
								}
							}
							else
							{
								Log.error(args, 3, "The database name was not alphanumeric (A-Za-z0-9_-).");
							}
						}
						else
						{
							Log.error(args, 2, "No database name was specified.");
						}
					}
					else
					{
						Log.error(args, args.size() == 2 ? 1 : 0, "Missing \"* FROM\" after \"SELECT\" command.");
					}
					break;
				}
				case INSERT: // Handle an INSERT command.
				{
					if (args.size() >= 2)
					{
						if (args.get(1).equalsIgnoreCase(INTO))
						{
							Log.error(args, 1, "No value was provided to INSERT.");
							return;
						}

						if (args.size() >= 3 && args.get(2).equalsIgnoreCase(INTO))
						{
							if (args.size() >= 4)
							{
								// Validate the name of the database.
								String database = args.get(3);
								if (checkAlphanumeric(database))
								{
									if (args.size() >= 6)
									{
										String table = args.get(5);
										if (args.get(4).equals(DOT))
										{
											// Validate the name of the table.
											if (checkAlphanumeric(table))
											{
												String value = args.get(1);
												try
												{
													// Send the INSERT command to the StringDatabase.
													this.database.insert(database, table, value);
													Log.status(args, "Values \"" + value + "\" was inserted into table \"" + table + "\" in database \"" + database + "\".");
												}
												catch (SQLException e)
												{
													Log.error(args, e.getMessage());
												}
											}
											else
											{
												Log.error(args, 5, "The table name was not alphanumeric (A-Za-z0-9_-).");
											}
										}
										else
										{
											Log.error(args, 4, "The table and database names were not separated by (\".\").");
										}
									}
									else
									{
										Log.error(args, args.size() - 1, "No table name was specified.");
									}
								}
								else
								{
									Log.error(args, 3, "The database name was not alphanumeric (A-Za-z0-9_-).");
								}
							}
							else
							{
								Log.error(args, 2, "No database name was specified.");
							}
						}
						else
						{
							Log.error(args, 1, "Missing \"INTO\" command.");
						}
					}
					else
					{
						Log.error(args, 0, "No value was provided to INSERT.");
					}
					break;
				}
				case UPDATE: // Handle an UPDATE command.
				{
					if (args.size() >= 2)
					{
						// Validate the name of the database.
						String database = args.get(1);
						if (checkAlphanumeric(database))
						{
							if (args.size() >= 4)
							{
								String table = args.get(3);
								if (args.get(2).equals(DOT))
								{
									// Validate the name of the table.
									if (checkAlphanumeric(table))
									{
										if (args.size() >= 5)
										{
											// Begin the process of parsing out the set value.
											if (args.get(4).equalsIgnoreCase("SET"))
											{
												if (args.size() >= 6)
												{
													if (checkAlphanumeric(args.get(5)))
													{
														if (args.size() >= 7)
														{
															if (args.size() >= 8)
															{
																// Begin the process of parsing out the search value.
																String value = args.get(7);
																if (args.size() >= 9 && args.get(8).equalsIgnoreCase(WHERE))
																{
																	if (args.size() >= 10)
																	{
																		if (checkAlphanumeric(args.get(9)))
																		{
																			if (args.size() >= 11 && args.get(10).equals(EQUALS))
																			{
																				if (args.size() >= 12)
																				{
																					String search = args.get(11);
																					try
																					{
																						// Send the UPDATE command to the StringDatabase.
																						long rows = this.database.update(database, table, value, search);
																						Log.status(args, rows + " row(s) matching \"" + search + "\" were updated to value \"" + value + "\" in table \"" + table + "\" in database \"" + database + "\".");
																					}
																					catch (SQLException e)
																					{
																						Log.error(args, e.getMessage());
																					}
																				}
																				else
																				{
																					Log.error(args, 10, "No search value provided after equals sign.");
																				}
																			}
																			else
																			{
																				Log.error(args, args.size() >= 11 ? 10 : 9, "Column name was not denoted with an equals sign.");
																			}
																		}
																		else
																		{
																			Log.error(args, 9, "Column name was not alphanumeric.");
																		}
																	}
																	else
																	{
																		Log.error(args, 8, "Missing column name after \"WHERE\" command.");
																	}
																}
																else
																{
																	Log.error(args, 7, "Missing \"WHERE\" command.");
																}
															}
															else
															{
																Log.error(args, 6, "Missing column name after \"WHERE\" command.");
															}
														}
														else
														{
															Log.error(args, 5, "Column name was not denoted with an equals sign.");
														}
													}
													else
													{
														Log.error(args, 5, "Column name was not alphanumeric.");
													}
												}
												else
												{
													Log.error(args, 4, "Missing column name after \"SET\" command.");
												}
											}
											else
											{
												Log.error(args, 4, "An unrecognized command was entered.");
											}
										}
										else
										{
											Log.error(args, 3, "Missing \"SET\" command after table name.");
										}
									}
									else
									{
										Log.error(args, 3, "The table name was not alphanumeric (A-Za-z0-9_-).");
									}
								}
								else
								{
									Log.error(args, 2, "The table and database names were not separated by (\".\").");
								}
							}
							else
							{
								Log.error(args, args.size() - 1, "No table name was specified.");
							}
						}
						else
						{
							Log.error(args, 1, "The database name was not alphanumeric (A-Za-z0-9_-).");
						}
					}
					else
					{
						Log.error(args, 0, "No database name was specified.");
					}
					break;
				}
				case DELETE: // Handle a DELETE command.
				{
					if (args.size() >= 2 && args.get(1).equalsIgnoreCase(FROM))
					{
						if (args.size() >= 3)
						{
							// Validate the name of the database.
							String database = args.get(2);
							if (checkAlphanumeric(database))
							{
								if (args.size() >= 5)
								{
									String table = args.get(4);
									if (args.get(3).equals(DOT))
									{
										// Validate the name of the table.
										if (checkAlphanumeric(table))
										{
											if (args.size() == 5)
											{
												try
												{
													// Send the DELETE WHERE 1 command to the StringDatabase.
													long rows = this.database.delete(database, table, null);
													Log.status(args, rows + " row(s) were deleted from table \"" + table + "\" in database \"" + database + "\".");
												}
												catch (SQLException e)
												{
													Log.error(args, e.getMessage());
												}
											}
											else
											{
												// Begin the process of parsing out the search value.
												if (args.get(5).equalsIgnoreCase(WHERE))
												{
													if (args.size() >= 7)
													{
														if (checkAlphanumeric(args.get(6)))
														{
															if (args.size() >= 8)
															{
																if (args.get(7).equalsIgnoreCase(EQUALS))
																{
																	if (args.size() >= 9)
																	{
																		String value = args.get(8);
																		try
																		{
																			// Send the DELETE WHERE VALUE = ? command to the StringDatabase.
																			long rows = this.database.delete(database, table, value);
																			Log.status(args, rows + " row(s) matching \"" + value + "\" were deleted from table \"" + table + "\" in database \"" + database + "\".");
																		}
																		catch (SQLException e)
																		{
																			Log.error(args, e.getMessage());
																		}
																	}
																	else
																	{
																		Log.error(args, 7, "No search value provided after equals sign.");
																	}
																}
																else
																{
																	Log.error(args, 6, "Column name was not denoted with an equals sign.");
																}
															}
															else
															{
																Log.error(args, 6, "No search value was given.");
															}
														}
														else
														{
															Log.error(args, 6, "Column name was not alphanumeric.");
														}
													}
													else
													{
														Log.error(args, 5, "Missing column name after \"WHERE\" command.");
													}
												}
												else
												{
													Log.error(args, 5, "An unrecognized command was entered.");
												}
											}
										}
										else
										{
											Log.error(args, 4, "The table name was not alphanumeric (A-Za-z0-9_-).");
										}
									}
									else
									{
										Log.error(args, 3, "The table and database names were not separated by (\".\").");
									}
								}
								else
								{
									Log.error(args, args.size() - 1, "No table name was specified.");
								}
							}
							else
							{
								Log.error(args, 2, "The database name was not alphanumeric (A-Za-z0-9_-).");
							}
						}
						else
						{
							Log.error(args, 1, "No database name was specified.");
						}
					}
					else
					{
						Log.error(args, args.size() == 2 ? 1 : 0, "Missing \"FROM\" after \"DELETE\" command.");
					}
					break;
				}
				default:
				{
					Log.error(args, "An unknown command was entered.");
					break;
				}
			}
		}
		else
		{
			Log.error(args, "No command entered.");
		}
	}

	/**
	 * Uses a REGEX pattern matching to extract out individual arguments,
	 * then uses REGEX find and replace to clean up the extracted arguments
	 * so that they can be easily handled by the parser.
	 *
	 * @param input The user input.
	 *
	 * @return A list of parsed command arguments.
	 */
	private List<String> clean(String input)
	{
		Matcher matcher = PATTERN.matcher(input.trim().replaceAll(";", ""));
		List<String> splits = new ArrayList<>();
		while (matcher.find()) splits.add(matcher.group().replaceAll("['“”\"]", ""));
		return splits;
	}

	/**
	 * Use REGEX to determine if a string is purely comprised of alphanumeric, dash,
	 * and underscore characters.
	 *
	 * @param s The string to check.
	 *
	 * @return <code>true</code> if the string is alphanumeric. <code>false</code> otherwise.
	 */
	private boolean checkAlphanumeric(String s)
	{
		return s.matches("[A-Za-z0-9_\\- ]+");
	}
}
