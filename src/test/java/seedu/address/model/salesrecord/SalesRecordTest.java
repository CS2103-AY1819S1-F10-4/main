package seedu.address.model.salesrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_RECORD_TWO;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_ONE;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_THREE;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_TWO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.salesrecords.RecordBuilder;

public class SalesRecordTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameRecord() {
        // same object -> returns true
        assertTrue(RECORD_THREE.isSameRecord(RECORD_THREE));

        // null -> returns false
        assertFalse(RECORD_THREE.isSameRecord(null));

        // different date and item name -> returns false
        SalesRecord editedRecordOne =
                new RecordBuilder(RECORD_ONE)
                        .withDate(VALID_DATE_RECORD_TWO)
                        .withName(VALID_ITEM_NAME_RECORD_TWO)
                        .build();
        assertFalse(RECORD_ONE.isSameRecord(editedRecordOne));

        // different date, same item name -> returns false
        editedRecordOne = new RecordBuilder(RECORD_ONE).withDate(VALID_DATE_RECORD_TWO).build();
        assertFalse(RECORD_ONE.isSameRecord(editedRecordOne));

        // different name, same date -> returns false
        editedRecordOne = new RecordBuilder(RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_TWO).build();
        assertFalse(RECORD_ONE.isSameRecord(editedRecordOne));

        // same date, same name, different attributes -> returns true
        editedRecordOne =
                new RecordBuilder(RECORD_ONE)
                        .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO)
                        .withPrice(VALID_PRICE_RECORD_TWO)
                        .build();
        assertTrue(RECORD_ONE.isSameRecord(editedRecordOne));

        // same date, same name, same quantity sold, different price -> returns true
        editedRecordOne =
                new RecordBuilder(RECORD_ONE)
                        .withPrice(VALID_PRICE_RECORD_TWO)
                        .build();
        assertTrue(RECORD_ONE.isSameRecord(editedRecordOne));

        // same date, same name, same price, different quantity sold -> returns true
        editedRecordOne =
                new RecordBuilder(RECORD_ONE)
                        .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO)
                        .build();
        assertTrue(RECORD_ONE.isSameRecord(editedRecordOne));
    }

    @Test
    public void equals() {
        // same values -> returns true
        SalesRecord recordThreeCopy = new RecordBuilder(RECORD_THREE).build();
        assertTrue(RECORD_THREE.equals(recordThreeCopy));

        // same object -> returns true
        assertTrue(RECORD_THREE.equals(RECORD_THREE));

        // null -> returns false
        assertFalse(RECORD_THREE.equals(null));

        // different type -> returns false
        assertFalse(RECORD_THREE.equals(5));

        // different person -> returns false
        assertFalse(RECORD_THREE.equals(RECORD_TWO));

        // different date -> returns false
        SalesRecord editedRecordOne = new RecordBuilder(RECORD_ONE).withDate(VALID_DATE_RECORD_TWO).build();
        assertFalse(RECORD_ONE.equals(editedRecordOne));

        // different item name -> returns false
        editedRecordOne = new RecordBuilder(RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_TWO).build();
        assertFalse(RECORD_ONE.equals(editedRecordOne));

        // different quantity sold -> returns false
        editedRecordOne = new RecordBuilder(RECORD_ONE).withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO).build();
        assertFalse(RECORD_ONE.equals(editedRecordOne));

        // different price -> returns false
        editedRecordOne = new RecordBuilder(RECORD_ONE).withPrice(VALID_PRICE_RECORD_TWO).build();
        assertFalse(RECORD_ONE.equals(editedRecordOne));
    }
}

