package seedu.address.ui.sales;

import java.text.NumberFormat;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.salesrecord.ItemName;
import seedu.address.model.salesrecord.Price;
import seedu.address.model.salesrecord.QuantitySold;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.model.salesrecord.SalesReport;
import seedu.address.ui.UiPart;

/**
 * Controller for a sales report window
 */
public class SalesReportWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SalesReportWindow.class);
    private static final String FXML = "SalesReportWindow.fxml";
    private static final String totalRevenueMessage = "Total revenue: %s";
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    private SalesReport salesReport;

    @FXML
    private TableView<SalesRecord> recordsTable;
    @FXML
    private TableColumn<SalesRecord, ItemName> itemName;
    @FXML
    private TableColumn<SalesRecord, QuantitySold> quantitySold;
    @FXML
    private TableColumn<SalesRecord, Price> price;
    @FXML
    private TableColumn<SalesRecord, Double> revenue;
    @FXML
    private Label totalRevenue;

    /**
     * Creates a new SalesReportWindow
     *
     * @param root Stage to use as the root of the SalesReportWindow.
     * @param salesReport the {@code SalesReport} to display in this window
     */
    public SalesReportWindow(Stage root, SalesReport salesReport) {
        super(FXML, root);
        root.setTitle(salesReport.toString());
        this.salesReport = salesReport;
    }

    /**
     * Creates a new SalesReportWindow.
     */
    public SalesReportWindow(SalesReport salesReport) {
        this(new Stage(), salesReport);
    }

    /**
     * Shows the SalesReportWindow.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Displaying sales report.");
        initialize();
        getRoot().show();
    }

    /**
     * Initializes the SalesReportWindow according to the sales report
     */
    private void initialize() {
        itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantitySold.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        initializePriceColumn();
        initializeRevenueColumn();
        recordsTable.setItems(salesReport.getRecords());
        totalRevenue.setText(String.format(totalRevenueMessage,
                currencyFormatter.format(salesReport.getTotalRevenue())));
    }

    /**
     * Initializes the price column in the TableView according to the records in the sales report
     */
    private void initializePriceColumn() {
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(cell -> new TableCell<>() {
            @Override
            protected void updateItem(Price price, boolean empty) {
                super.updateItem(price, empty);
                setText((empty) ? null : currencyFormatter.format(price.getValue()));
            }
        });
    }

    /**
     * Initializes the revenue column in the TableView according to the records in the sales report
     */
    private void initializeRevenueColumn() {
        revenue.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        revenue.setCellFactory(cell -> new TableCell<>() {
            @Override
            protected void updateItem(Double revenue, boolean empty) {
                super.updateItem(revenue, empty);
                setText((empty) ? null : currencyFormatter.format(revenue));
            }
        });
    }

    /**
     * Returns true if the SalesReportWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }
}
