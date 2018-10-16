package guitests.guihandles.sales;

import guitests.GuiRobot;
import guitests.guihandles.StageHandle;
import javafx.stage.Stage;
import seedu.address.model.salesrecord.SalesReport;

/**
 * A handle to the {@code SalesReportWindow} of the application.
 */
public class SalesReportWindowHandle extends StageHandle {

    private final SalesReport salesReport;

    public SalesReportWindowHandle(Stage salesReportWindowStage, SalesReport salesReport) {
        super(salesReportWindowStage);
        this.salesReport = salesReport;
    }

    /**
     * Returns true if a sales report window is currently present in the application.
     */
    public boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(salesReport.toString());
    }
}
