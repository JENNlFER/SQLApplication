package jteissler.csci1302.sqlgui;

import javafx.scene.control.TextArea;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author J Teissler
 * @date 3/15/17
 */
public class CommandSelector
{
	private final TextArea textArea;

	public CommandSelector(TextArea textArea)
	{
		this.textArea = textArea;
	}

	/**
	 * @return All the content of the text area.
	 */
	public List<String> getContent()
	{
		return Arrays.asList(textArea.getText().split("\\r\\n?|\\n"));
	}

	/**
	 * Sets the current content of the text area.
	 *
	 * @param content Lines of content.
	 */
	public void setContent(List<String> content)
	{
		textArea.setText(String.join(System.lineSeparator(), content));
	}

	/**
	 * @return The current command selected in the text area.
	 */
	public Optional<String> getCommand()
	{
		String selection = textArea.getSelectedText()
	}

	/**
	 * @return All commands currently in the text area.
	 */
	public List<String> getAllCommands()
	{

	}

}
