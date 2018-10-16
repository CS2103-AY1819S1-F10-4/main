package seedu.address.logic.parser.salescommandsparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;

import java.util.stream.Stream;

import seedu.address.logic.commands.salescommands.RecordSalesCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.ItemName;
import seedu.address.model.salesrecord.Price;
import seedu.address.model.salesrecord.QuantitySold;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Parses input arguments and creates a new RecordSalesCommand object
 */
public class RecordSalesCommandParser implements Parser<RecordSalesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordSalesCommand
     * and returns a RecordSalesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordSalesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ITEM_NAME, PREFIX_QUANTITY_SOLD,
                        PREFIX_ITEM_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_ITEM_NAME, PREFIX_QUANTITY_SOLD, PREFIX_ITEM_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordSalesCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        ItemName name = ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM_NAME).get());
        QuantitySold quantitySold = ParserUtil.parseQuantitySold(argMultimap.getValue(PREFIX_QUANTITY_SOLD).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_ITEM_PRICE).get());

        SalesRecord record = new SalesRecord(date, name, quantitySold, price);

        return new RecordSalesCommand(record);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
