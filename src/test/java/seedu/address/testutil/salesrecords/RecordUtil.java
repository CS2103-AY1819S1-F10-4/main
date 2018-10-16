package seedu.address.testutil.salesrecords;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;

import seedu.address.logic.commands.salescommands.RecordSalesCommand;
import seedu.address.model.salesrecord.SalesRecord;
/**
 * A utility class for {@code SalesRecord}.
 */
public class RecordUtil {
    /**
     * Returns a record-sales command string for adding the {@code record}.
     */
    public static String getRecordSalesCommand(SalesRecord record) {
        return RecordSalesCommand.COMMAND_WORD + " " + getRecordDetails(record);
    }
    /**
     * Returns the part of command string for the given {@code record}'s details.
     */
    public static String getRecordDetails(SalesRecord record) {
        return PREFIX_DATE + record.getDate().toString() + " "
                + PREFIX_ITEM_NAME + record.getName().toString() + " "
                + PREFIX_QUANTITY_SOLD + record.getQuantitySold().toString() + " "
                + PREFIX_ITEM_PRICE + record.getPrice().toString();
    }
}


