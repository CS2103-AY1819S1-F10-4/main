package seedu.address.testutil.salesrecords;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_RECORD_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_RECORD_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_RECORD_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.salesrecord.SalesRecord;

/**
 * A utility class containing a list of {@code SalesRecord} objects to be used in tests.
 */
public class TypicalRecords {
    public static final SalesRecord RECORD_DEFAULT = new RecordBuilder().build();
    public static final SalesRecord RECORD_ONE = new RecordBuilder()
            .withDate(VALID_DATE_RECORD_ONE)
            .withName(VALID_ITEM_NAME_RECORD_ONE)
            .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_ONE)
            .withPrice(VALID_PRICE_RECORD_ONE)
            .build();
    public static final SalesRecord RECORD_TWO = new RecordBuilder()
            .withDate(VALID_DATE_RECORD_TWO)
            .withName(VALID_ITEM_NAME_RECORD_TWO)
            .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO)
            .withPrice(VALID_PRICE_RECORD_TWO).build();
    public static final SalesRecord RECORD_THREE = new RecordBuilder()
            .withDate(VALID_DATE_RECORD_THREE)
            .withName(VALID_ITEM_NAME_RECORD_THREE)
            .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_THREE)
            .withPrice(VALID_PRICE_RECORD_THREE).build();

    private TypicalRecords() {} // prevents instantiation
    public static List<SalesRecord> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(RECORD_DEFAULT, RECORD_ONE, RECORD_TWO));
    }
}
