package seedu.address.ui.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import seedu.address.model.salesrecord.SalesReport;
import seedu.address.testutil.salesrecords.ReportBuilder;
import seedu.address.ui.GuiUnitTest;

public class SalesReportWindowTest extends GuiUnitTest {

    private SalesReportWindow salesReportWindow;
    //private SalesReportWindowHandle salesReportWindowHandle; // Handle to be used in the future

    @Before
    public void setUp() throws Exception {
        SalesReport report = new ReportBuilder().build();
        guiRobot.interact(() -> salesReportWindow = new SalesReportWindow(report));
        FxToolkit.registerStage(salesReportWindow::getRoot);
        //salesReportWindowHandle = new SalesReportWindowHandle(salesReportWindow.getRoot(), report);
    }

    @Test
    public void isShowing_salesReportWindowIsShowing_returnsTrue() {
        guiRobot.interact(salesReportWindow::show);
        assertTrue(salesReportWindow.isShowing());
    }

    @Test
    public void isShowing_salesReportIsHiding_returnsFalse() {
        assertFalse(salesReportWindow.isShowing());
    }
}
