package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.salesrecord.SalesReport;

/**
 * An event requesting to display the sales report of a specified date
 */
public class DisplaySalesReportEvent extends BaseEvent {

    private SalesReport salesReportToDisplay;

    public DisplaySalesReportEvent(SalesReport salesReportToDisplay) {
        this.salesReportToDisplay = salesReportToDisplay;
    }

    public SalesReport getSalesReportToDisplay() {
        return salesReportToDisplay;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
