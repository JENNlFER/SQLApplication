package jteissler.csci1302.simplesql;

/**
 * Parser interface which defines a parse method and documents
 * which SQL methods should be supported by the parser.
 *
 * @author J Teissler
 * @date 2/6/17
 */
public interface Parser
{
	/**
	 * Parses out SQL commands, and sends the results to a database. <br>
	 *
	 * <b>Minimum Parsable Commands</b>
	 * <ul>
	 *     <li>CREATE DATABASE {database}</li>
	 *     <li>CREATE TABLE {database}.{table}</li>
	 *     <li>DROP DATABASE {database}</li>
	 *     <li>DROP TABLE {database}.{table}</li>
	 *     <li>INSERT "{value}" INTO {database}.{table}</li>
	 *     <li>UPDATE {database}.{table} SET COLUMN = {value1} WHERE COLUMN = {value2}</li>
	 *     <li>DELETE FROM {database}.{table}</li>
	 *     <li>DELETE FROM {database}.{table} WHERE COLUMN = {value}</li>
	 *     <li>SELECT * FROM {database}.{table}</li>
	 *     <li>SELECT * FROM {database}.{table} WHERE COLUMN = {value}</li>
	 * </ul>
	 *
	 * @param input The user input string.
	 */
	void parse(String input);
}
