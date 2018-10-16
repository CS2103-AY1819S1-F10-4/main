package seedu.address.logic.commands.sales;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplaySalesReportEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.SalesReport;

/**
 * Display sales report of a specific date
 */
public class DisplaySalesCommand extends Command {
    public static final String COMMAND_WORD = "display-sales";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generate and display the sales report of a specified "
            + "date.\n"
            + "Parameters: DATE (must be in DD-MM-YYYY format) "
            + "Example: " + COMMAND_WORD + " 25-09-2018";

    public static final String DISPLAYING_REPORT_MESSAGE = "Displayed sales report dated: %1$s";

    private final Date date;

    /**
     * @param date of sales report to be displayed
     */
    public DisplaySalesCommand(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        SalesReport salesReport = model.getSalesReport(date);
        EventsCenter.getInstance().post(new DisplaySalesReportEvent(salesReport));
        return new CommandResult(String.format(DISPLAYING_REPORT_MESSAGE, date.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplaySalesCommand // instanceof handles nulls
                    && date.equals(((DisplaySalesCommand) other).date));
    }
}
