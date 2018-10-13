package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.accounts.RegisterCommand;
import seedu.address.logic.commands.menu.AddItemCommand;
import seedu.address.logic.commands.menu.ClearMenuCommand;
import seedu.address.logic.commands.menu.DeleteItemCommand;
import seedu.address.logic.commands.menu.EditItemCommand;
import seedu.address.logic.commands.menu.FilterMenuCommand;
import seedu.address.logic.commands.menu.FindItemCommand;
import seedu.address.logic.commands.menu.ListItemsCommand;
import seedu.address.logic.commands.menu.SelectItemCommand;
import seedu.address.logic.commands.menu.SortMenuCommand;
import seedu.address.logic.commands.salescommands.RecordSalesCommand;
import seedu.address.logic.parser.accounts.RegisterCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.menu.AddItemCommandParser;
import seedu.address.logic.parser.menu.DeleteItemCommandParser;
import seedu.address.logic.parser.menu.EditItemCommandParser;
import seedu.address.logic.parser.menu.FilterMenuCommandParser;
import seedu.address.logic.parser.menu.FindItemCommandParser;
import seedu.address.logic.parser.menu.SelectItemCommandParser;
import seedu.address.logic.parser.menu.SortMenuCommandParser;
import seedu.address.logic.parser.salescommandsparser.RecordSalesCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case RecordSalesCommand.COMMAND_WORD:
            return new RecordSalesCommandParser().parse(arguments);

        case RegisterCommand.COMMAND_WORD:
        case RegisterCommand.COMMAND_ALIAS:
            return new RegisterCommandParser().parse(arguments);

        case AddItemCommand.COMMAND_WORD:
        case AddItemCommand.COMMAND_ALIAS:
            return new AddItemCommandParser().parse(arguments);

        case DeleteItemCommand.COMMAND_WORD:
        case DeleteItemCommand.COMMAND_ALIAS:
            return new DeleteItemCommandParser().parse(arguments);

        case EditItemCommand.COMMAND_WORD:
        case EditItemCommand.COMMAND_ALIAS:
            return new EditItemCommandParser().parse(arguments);

        case ListItemsCommand.COMMAND_WORD:
        case ListItemsCommand.COMMAND_ALIAS:
            return new ListItemsCommand();

        case SelectItemCommand.COMMAND_WORD:
        case SelectItemCommand.COMMAND_ALIAS:
            return new SelectItemCommandParser().parse(arguments);

        case FindItemCommand.COMMAND_WORD:
        case FindItemCommand.COMMAND_ALIAS:
            return new FindItemCommandParser().parse(arguments);

        case SortMenuCommand.COMMAND_WORD:
        case SortMenuCommand.COMMAND_ALIAS:
            return new SortMenuCommandParser().parse(arguments);

        case FilterMenuCommand.COMMAND_WORD:
        case FilterMenuCommand.COMMAND_ALIAS:
            return new FilterMenuCommandParser().parse(arguments);

        case ClearMenuCommand.COMMAND_WORD:
        case ClearMenuCommand.COMMAND_ALIAS:
            return new ClearMenuCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
