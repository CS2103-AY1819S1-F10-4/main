package seedu.address.ui.sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRecord;

import org.junit.Test;

import guitests.guihandles.sales.RecordCardHandle;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.testutil.salesrecords.RecordBuilder;
import seedu.address.ui.GuiUnitTest;

public class RecordCardTest extends GuiUnitTest {

    @Test
    public void display() {
        SalesRecord salesRecord = new RecordBuilder().build();
        RecordCard recordCard = new RecordCard(salesRecord, 1);
        uiPartRule.setUiPart(recordCard);
        assertCardDisplay(recordCard, salesRecord, 1);
    }

    @Test
    public void equals() {
        SalesRecord salesRecord = new RecordBuilder().build();
        RecordCard recordCard = new RecordCard(salesRecord, 0);

        // same record, same index -> returns true
        RecordCard copy = new RecordCard(salesRecord, 0);
        assertTrue(recordCard.equals(copy));

        // same object -> returns true
        assertTrue(recordCard.equals(recordCard));

        // null -> returns false
        assertFalse(recordCard.equals(null));

        // different types -> returns false
        assertFalse(recordCard.equals(0));

        // different record, same index -> returns false
        SalesRecord differentRecord = new RecordBuilder().withDate("09-09-2018").build();
        assertFalse(recordCard.equals(new RecordCard(differentRecord, 0)));

        // same record, different index -> returns false
        assertFalse(recordCard.equals(new RecordCard(salesRecord, 1)));
    }

    /**
     * Asserts that {@code recordCard} displays the details of {@code expectedRecord} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RecordCard recordCard, SalesRecord expectedRecord, int expectedId) {
        guiRobot.pauseForHuman();

        RecordCardHandle recordCardHandle = new RecordCardHandle(recordCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", recordCardHandle.getId());

        // verify record details are displayed correctly
        assertCardDisplaysRecord(expectedRecord, recordCardHandle);
    }
}
