package seedu.address.logic.parser.sales;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_QUANTITY_SOLD;
import static seedu.address.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.sales.RecordSalesCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.util.ArgumentMultimap;
import seedu.address.logic.parser.util.ArgumentTokenizer;
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
     * Parses the given {@code String} of arguments in the context of the RecordSalesCommand and returns a
     * RecordSalesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordSalesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_DATE, PREFIX_ITEM_NAME, PREFIX_QUANTITY_SOLD, PREFIX_ITEM_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_ITEM_NAME, PREFIX_QUANTITY_SOLD, PREFIX_ITEM_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordSalesCommand.MESSAGE_USAGE));
        }

        Date date = SalesParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        ItemName name = SalesParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM_NAME).get());
        QuantitySold quantitySold = SalesParserUtil.parseQuantitySold(argMultimap.getValue(PREFIX_QUANTITY_SOLD).get());
        Price price = SalesParserUtil.parsePrice(argMultimap.getValue(PREFIX_ITEM_PRICE).get());

        SalesRecord record = new SalesRecord(date, name, quantitySold, price);

        return new RecordSalesCommand(record);
    }
}
