package seedu.address.logic.commands.salescommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Record the sales volume of a menu item
 */
public class RecordSalesCommand extends Command {
    public static final String COMMAND_WORD = "record-sales";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records the sales volume of a menu item within a day"
            + " into the sales book.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (must be in DD-MM-YYYY format) "
            + PREFIX_ITEM_NAME + "ITEM_NAME "
            + PREFIX_QUANTITY_SOLD + "QUANTITY SOLD "
            + PREFIX_ITEM_PRICE + "ITEM_PRICE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + " 25-09-2018 "
            + PREFIX_ITEM_NAME + "Fried Rice "
            + PREFIX_QUANTITY_SOLD + "35 "
            + PREFIX_ITEM_PRICE + "5.50";

    public static final String MESSAGE_RECORD_SALES_SUCCESS = "Sales volume recorded.\n%1$s";
    public static final String MESSAGE_DUPLICATE_SALES_RECORD = "Sales record of \"%1$s\" already exists on the same "
            + "date.";

    private final SalesRecord toAdd;

    /**
     * @param salesRecord to be appended to the sales book
     */
    public RecordSalesCommand(SalesRecord salesRecord) {
        requireNonNull(salesRecord);
        this.toAdd = salesRecord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasRecord(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_SALES_RECORD, toAdd.getName()));
        }

        model.addRecord(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_RECORD_SALES_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordSalesCommand // instanceof handles nulls
                    && toAdd.equals(((RecordSalesCommand) other).toAdd));
    }
}
