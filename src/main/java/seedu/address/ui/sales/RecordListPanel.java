package seedu.address.ui.sales;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RecordPanelSelectionChangedEvent;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of sales records.
 */
public class RecordListPanel extends UiPart<Region> {
    private static final String FXML = "RecordListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecordListPanel.class);

    @FXML
    private ListView<SalesRecord> recordListView;

    public RecordListPanel(ObservableList<SalesRecord> recordList) {
        super(FXML);
        setConnections(recordList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<SalesRecord> recordList) {
        recordListView.setItems(recordList);
        recordListView.setCellFactory(listView -> new RecordListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        recordListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in record list panel changed to : '" + newValue + "'");
                        raise(new RecordPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code RecordCard} at the {@code index} and selects it.
     */
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            recordListView.scrollTo(index);
            recordListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code SalesRecord} using a {@code RecordCard}.
     */
    class RecordListViewCell extends ListCell<SalesRecord> {
        @Override
        protected void updateItem(SalesRecord salesRecord, boolean empty) {
            super.updateItem(salesRecord, empty);

            if (empty || salesRecord == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecordCard(salesRecord, getIndex() + 1).getRoot());
            }
        }
    }

}
