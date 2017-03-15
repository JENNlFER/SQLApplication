package jteissler.csci1302.simplesql;

import java.util.List;

/**
 * Simple SQL-like database methods which allow for tables with multiple columns
 * of only string type data. Features like auto-increment and primary or unique
 * keys are not implemented. <br>
 *
 * Database and table names should contain no whitespace and conform to standard
 * UNIX filename conventions in terms of character set.
 *
 * @author J Teissler
 * @date 2/5/17
 */
public interface StringDatabase
{
	/**
	 * Creates a new, distinct database. If a database already exists with the same name
	 * no new database will be created.
	 *
	 * @param database The name of the new database.
	 *
	 * @throws SQLException if an issue arose while creating the database.
	 */
	void createDatabase(String database) throws SQLException;

	/**
	 * Deletes a database and all of its tables.
	 *
	 * @param database The name of the database to delete.
	 *
	 * @throws SQLException if an issue arose while dropping the database.
	 */
	void dropDatabase(String database) throws SQLException;

	/**
	 * Creates a new table in the specified database. If a table already exists in
	 * the database then no new table will be created.
	 *
	 * @param database The name of the database to modify.
	 * @param table The name of the table to create.
	 *
	 * @throws SQLException if an issue arose while creating the table.
	 */
	void createTable(String database, String table) throws SQLException;

	/**
	 * Deletes an existing table from the specified database.
	 *
	 * @param database The name of the database to modify.
	 * @param table The table to delete from the database.
	 *
	 * @throws SQLException if an issue arose while dropping the table.
	 */
	void dropTable(String database, String table) throws SQLException;

	/**
	 * Inserts a new entry into a table in a database.
	 *
	 * @param database The name of the database to modify.
	 * @param table The table into which to insert the new data.
	 * @param value The value to store.
	 *
	 * @throws SQLException if an issue arose while inserting the value.
	 */
	void insert(String database, String table, String value) throws SQLException;

	/**
	 * Modifies one or more existing rows in a table.
	 *
	 * @param database The name of the database to modify.
	 * @param table The table which to modify.
	 * @param value The value to set.
	 * @param search The value which will prompt a change to a row. <code>null</code> for all rows.
	 *
	 * @return The number of rows that were updated.
	 *
	 * @throws SQLException if an issue arose while updating the table.
	 */
	long update(String database, String table, String value, String search) throws SQLException;

	/**
	 * Deletes one or more existing rows in a table.
	 *
	 * @param database The name of the database to modify.
	 * @param table The table which to modify.
	 * @param search The value to search for.
	 *
	 * @return The number of rows that were deleted.
	 *
	 * @throws SQLException if an issue arose while deleting values from the table.
	 */
	long delete(String database, String table, String search) throws SQLException;

	/**
	 * Selects one or more rows of data from a table.
	 *
	 * @param database The name of the database to search.
	 * @param table The table which to search.
	 * @param search The value to search for. <code>null</code> for all values.
	 *
	 * @return The selected string values in a list associated with their row number.
	 *
	 * @throws SQLException if an issue arose while selecting values from the table.
	 */
	List<Pair<Long, String>> select(String database, String table, String search) throws SQLException;

}
