package seedu.address.model.reservation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PaxTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Pax(null));
    }

    @Test
    public void constructor_invalidPax_throwsIllegalArgumentException() {
        String invalidPax = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Pax(invalidPax));
    }

    @Test
    public void isValidPax() {
        // null pax number
        Assert.assertThrows(NullPointerException.class, () -> Pax.isValidPax(null));

        // invalid pax numbers
        assertFalse(Pax.isValidPax("")); // empty string
        assertFalse(Pax.isValidPax(" ")); // spaces only
        assertFalse(Pax.isValidPax("four")); // non-numeric
        assertFalse(Pax.isValidPax("9011p041")); // alphabets within digits
        assertFalse(Pax.isValidPax("9312 1534")); // spaces within digits

        // valid pax numbers
        assertTrue(Pax.isValidPax("4")); // at least 1 number
        assertTrue(Pax.isValidPax("12"));
        assertTrue(Pax.isValidPax("12124685967")); // long pax numbers (if you can fit this many people I guess)
    }
}
