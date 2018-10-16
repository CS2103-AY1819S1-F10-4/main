package guitests.guihandles.sales;

import guitests.guihandles.NodeHandle;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.address.model.salesrecord.SalesRecord;

/**
 * Provides a handle to an item card in the item list panel.
 */
public class RecordCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DATE_FIELD_ID = "#date";
    private static final String ITEM_NAME_FIELD_ID = "#itemName";
    private static final String QUANTITY_SOLD_FIELD_ID = "#quantitySold";
    private static final String PRICE_FIELD_ID = "#price";

    private final Label idLabel;
    private final Label dateLabel;
    private final Label itemNameLabel;
    private final Label quantitySoldLabel;
    private final Label priceLabel;

    public RecordCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        itemNameLabel = getChildNode(ITEM_NAME_FIELD_ID);
        quantitySoldLabel = getChildNode(QUANTITY_SOLD_FIELD_ID);
        priceLabel = getChildNode(PRICE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getItemName() {
        return itemNameLabel.getText();
    }

    public String getQuantitySold() {
        return quantitySoldLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code item}.
     */
    public boolean equals(SalesRecord salesRecord) {
        return getDate().equals(salesRecord.getDate().toString())
                && getItemName().equals(salesRecord.getName().toString())
                && getQuantitySold().equals(salesRecord.getQuantitySold().toString())
                && getPrice().equals(salesRecord.getPrice().toString());
    }
}
