package seedu.address.logic.parser.sales;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.sales.DisplaySalesCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.salesrecord.Date;

/**
 * Parses input arguments and creates a new DisplaySalesCommand object
 */
public class DisplaySalesCommandParser implements Parser<DisplaySalesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplaySalesCommand
     * and returns an DisplaySalesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplaySalesCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplaySalesCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(args);
        return new DisplaySalesCommand(date);
    }
}
