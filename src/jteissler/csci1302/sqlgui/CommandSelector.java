package jteissler.csci1302.sqlgui;

import javafx.scene.control.TextArea;

/**
 * Selects the current command or commands in the command input area.
 *
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
	public String getContent()
	{
		return textArea.getText();
	}

	/**
	 * Sets the current content of the text area.
	 *
	 * @param content Lines of content.
	 */
	public void setContent(String content)
	{
		textArea.setText(String.join(System.lineSeparator(), content));
	}

	/**
	 * @return The current command selected in the text area.
	 */
	public String[] getCommand()
	{
		String selection;

		if (WorkbenchOptions.EXECUTE_HIGHLIGHTED)
		{
			selection = textArea.getSelectedText().trim();

			if (!selection.isEmpty())
			{
				switch (WorkbenchOptions.COMMAND_DIVIDER)
				{
					case SEMICOLON:
					{
						return selection.split(";");
					}
					case EACH_LINE:
					{
						return selection.split("\\r\\n?|\\n");
					}
					case EMPTY_LINE:
					{
						return selection.split("[\\r?\\n|\\n][\\t ]?[\\r?\\n|\\n]");
					}
				}
			}
		}

		selection = textArea.getText();
		String[] split = null;

		switch (WorkbenchOptions.COMMAND_DIVIDER)
		{
			case SEMICOLON:
			{
				split = selection.split(";");
				break;
			}
			case EACH_LINE:
			{
				split = selection.split("\\r\\n?|\\n");
				break;
			}
			case EMPTY_LINE:
			{
				split = selection.split("[\\r?\\n|\\n][\\t ]?[\\r?\\n|\\n]");
				break;
			}
		}

		int cursor = textArea.getCaretPosition();

		int index = 0;
		for (String line : split)
		{
			if (cursor >= index && cursor < (index + line.length()))
			{
				return new String[]{line};
			}

			index += line.length();
		}

		return new String[0];
	}

	/**
	 * @return All commands currently in the text area.
	 */
	public String[] getAllCommands()
	{
		String selection = textArea.getText().trim();

		switch (WorkbenchOptions.COMMAND_DIVIDER)
		{
			case EACH_LINE:
			{
				return selection.split("\\r\\n?|\\n");
			}
			case EMPTY_LINE:
			{
				return selection.split("[\\r?\\n|\\n][\\t ]?[\\r?\\n|\\n]");
			}
			case SEMICOLON:
			default:
			{
				return selection.split(";");
			}
		}
	}
}
