package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NumUnitsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new NumUnits(null));
    }

    @Test
    public void constructor_invalidNumUnits_throwsIllegalArgumentException() {
        String invalidNumUnits = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new NumUnits(invalidNumUnits));
    }

    @Test
    public void isValidNumUnits() {
        // null number of units
        Assert.assertThrows(NullPointerException.class, () -> NumUnits.isValidNumUnits(null));

        // invalid number of units
        assertFalse(NumUnits.isValidNumUnits("")); // empty string
        assertFalse(NumUnits.isValidNumUnits(" ")); // spaces only
        assertFalse(NumUnits.isValidNumUnits("abcde^")); // only non-numeric characters
        assertFalse(NumUnits.isValidNumUnits("12ab*")); // contains non-numeric characters
        assertFalse(NumUnits.isValidNumUnits("200.2")); // numeric value with decimal separator

        // valid number of units
        assertTrue(NumUnits.isValidNumUnits("800")); // numeric value
        assertTrue(NumUnits.isValidNumUnits("28902375639")); // numeric value with many digits
    }
}
