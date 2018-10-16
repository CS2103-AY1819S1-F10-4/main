package seedu.address.model.salesrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "31-02-2018";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("19022018")); // Does not have "-" delimiter
        assertFalse(Date.isValidDate("my-bd-date")); // non-numeric
        assertFalse(Date.isValidDate("2nd February 2018")); // non-numeric
        assertFalse(Date.isValidDate("2nd May 18")); // non-numeric
        assertFalse(Date.isValidDate("122-02-2018")); // day does not have 2 digits
        assertFalse(Date.isValidDate("1-12-2018")); // day does not have 2 digits
        assertFalse(Date.isValidDate("12-13-2018")); // no such month
        assertFalse(Date.isValidDate("31-02-2018")); // no such day

        // valid dates
        assertTrue(Date.isValidDate("12-12-2012")); // follows format
        assertTrue(Date.isValidDate("31-01-2018"));
        assertTrue(Date.isValidDate("11-11-2017"));
    }
}
