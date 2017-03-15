package jteissler.csci1320.simplesql;

/**
 * Represents an exception that occurs while performing an SQL command.
 *
 * @author J Teissler
 * @date 2/8/17
 */
public class SQLException extends Exception
{
	/**
	 * Construct an SQLException
	 *
	 * @param message A message describing the cause of the exception.
	 */
	public SQLException(String message)
	{
		super(message);
	}
}
