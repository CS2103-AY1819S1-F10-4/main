package seedu.address.testutil.salesrecords;

import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.ItemName;
import seedu.address.model.salesrecord.Price;
import seedu.address.model.salesrecord.QuantitySold;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * A utility class to help with building SalesRecord objects.
 */
public class RecordBuilder {
    public static final String DEFAULT_DATE = "25-09-2018";
    public static final String DEFAULT_ITEM_NAME = "Fried Rice";
    public static final String DEFAULT_QUANTITY_SOLD = "35";
    public static final String DEFAULT_PRICE = "5.50";
    private Date date;
    private ItemName itemName;
    private QuantitySold quantitySold;
    private Price price;

    public RecordBuilder() {
        date = new Date(DEFAULT_DATE);
        itemName = new ItemName(DEFAULT_ITEM_NAME);
        quantitySold = new QuantitySold(DEFAULT_QUANTITY_SOLD);
        price = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     */
    public RecordBuilder(SalesRecord recordToCopy) {
        date = recordToCopy.getDate();
        itemName = recordToCopy.getName();
        quantitySold = recordToCopy.getQuantitySold();
        price = recordToCopy.getPrice();
    }

    /**
     * Sets the {@code Date} of the {@code Record} that we are building.
     */
    public RecordBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code ItemName} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.itemName = new ItemName(name);
        return this;
    }

    /**
     * Sets the {@code QuantitySold} of the {@code Record} that we are building.
     */
    public RecordBuilder withQuantitySold (String quantitySold) {
        this.quantitySold = new QuantitySold(quantitySold);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Record} that we are building.
     */
    public RecordBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    public SalesRecord build() {
        return new SalesRecord(date, itemName, quantitySold, price);
    }
}
