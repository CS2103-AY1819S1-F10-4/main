package seedu.address.logic.commands.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.sales.DisplaySalesCommand.DISPLAYING_REPORT_MESSAGE;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import seedu.address.commons.events.ui.DisplaySalesReportEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.salesrecord.Date;
import seedu.address.testutil.Assert;
import seedu.address.ui.testutil.EventsCollectorRule;

class DisplaySalesCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DisplaySalesCommand(null));
    }

    @Test
    public void execute_displaySales_success() {
        Date date = new Date(VALID_DATE_RECORD_ONE);
        assertCommandSuccess(new DisplaySalesCommand(date), model, commandHistory,
                String.format(DISPLAYING_REPORT_MESSAGE, date.toString()), expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplaySalesReportEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void equals() {
        Date date1 = new Date(VALID_DATE_RECORD_ONE);
        Date date2 = new Date(VALID_DATE_RECORD_TWO);

        DisplaySalesCommand displayDate1SalesReportCommand = new DisplaySalesCommand(date1);
        DisplaySalesCommand displayDate2SalesReportCommand = new DisplaySalesCommand(date2);

        // same object -> returns true
        assertTrue(displayDate1SalesReportCommand.equals(displayDate1SalesReportCommand));

        // same values -> returns true
        DisplaySalesCommand displayDate1SalesReportCommandCopy = new DisplaySalesCommand(date1);
        assertTrue(displayDate1SalesReportCommand.equals(displayDate1SalesReportCommandCopy));

        // different types -> returns false
        assertFalse(displayDate1SalesReportCommand.equals(1));

        // null -> returns false
        assertFalse(displayDate1SalesReportCommand.equals(null));

        // different person -> returns false
        assertFalse(displayDate1SalesReportCommand.equals(displayDate2SalesReportCommand));
    }
}
