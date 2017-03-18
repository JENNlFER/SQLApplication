package jteissler.csci1302.sqlgui;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	 * @return The current command selected in the text area.
	 */
	public List<String> getCommand()
	{
		if (textArea.getText().trim().isEmpty())
		{
			return new ArrayList<>();
		}

		if (WorkbenchOptions.doHighlightedCommands())
		{
			String selection = textArea.getSelectedText().trim();

			if (!selection.isEmpty())
			{
				String[] commands = null;
				List<String> cleanedCommands = new ArrayList<>();

				switch (WorkbenchOptions.getCommandDivider())
				{
					case SEMICOLON:
					{
						commands = selection.replaceAll("\\r\\n?|\\n", " ").split(";");
						break;
					}
					case EACH_LINE:
					{
						commands = selection.split("\\r\\n?|\\n");
						break;
					}
					case EMPTY_LINE:
					{
						commands = selection.split("[\\r?\\n|\\n][\\s ]*[\\r?\\n|\\n]");
						break;
					}
				}

				for (String command : commands)
				{
					if (!command.trim().isEmpty())
					{
						cleanedCommands.add(command.trim().replaceAll("\\r\\n?|\\n|\\t", " "));
					}
				}

				return cleanedCommands;
			}
		}

		String textString = textArea.getText();
		char[] text = textString.toCharArray();
		int cursor = textArea.getCaretPosition();
		int begin = 0;
		int end = 0;

		if (cursor > 0 && (text[cursor - 1] == '\n' || text[cursor - 1] == '\r') && (text.length > cursor + 1 && text[cursor + 1] != '\r' && text[cursor + 1] != '\n'))
		{
			return new ArrayList<>();
		}

		switch (WorkbenchOptions.getCommandDivider())
		{
			case SEMICOLON:
			{
				for (int i = 0; i < text.length; ++i)
				{
					if (text[i] == ';')
					{
						end = i;

						if (begin <= cursor && cursor <= end + 1)
						{
							break;
						}

						begin = i;
					}
				}

				break;
			}
			case EACH_LINE:
			{
				for (int i = 0; i < text.length; ++i)
				{
					if (text[i] == '\n' || text[i] == '\r')
					{
						++i;

						if (text[i - 1] == '\r' && text[i] == '\n' && i + 1 < text.length)
						{
							++i;
						}

						end = i;

						if (begin <= cursor && cursor < end)
						{
							break;
						}

						begin = i;
					}
				}

				break;
			}
			case EMPTY_LINE:
			{
				for (int i = 0; i < text.length; ++i)
				{
					int returnCount = 0;
					while (text[i] == '\n' || text[i] == '\r')
					{
						++i;
						++returnCount;

						if (text[i - 1] == '\r' && text[i] == '\n' && i + 1 < text.length)
						{
							++i;
						}
					}

					if (returnCount > 1 || i >= text.length - 1)
					{
						if (i >= text.length - 1)
						{
							end = text.length;
						}
						else
						{
							end = i;
						}

						if (begin <= cursor && cursor < end)
						{
							break;
						}

						begin = i;
					}
				}

				break;
			}
		}

		return Collections.singletonList(textString.substring(begin, end).replaceAll("\\r\\n?|\\n|\\t|;", " ").trim());
	}

	/**
	 * @return All commands currently in the text area.
	 */
	public List<String> getAllCommands()
	{
		String selection = textArea.getText().trim();

		String[] split = null;
		List<String> commands = new ArrayList<>();

		switch (WorkbenchOptions.getCommandDivider())
		{
			case SEMICOLON:
			{
				split = selection.replaceAll("\\r\\n?|\\n", " ").split(";");
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

		for (String command : split)
		{
			if (!command.trim().isEmpty())
			{
				commands.add(command.trim().replaceAll("\\r\\n?|\\n|\\t", " "));
			}
		}

		return commands;
	}
}
