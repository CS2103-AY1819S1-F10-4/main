package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MinimumUnitTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MinimumUnit(null));
    }

    @Test
    public void constructor_invalidMinimum_throwsIllegalArgumentException() {
        String invalidMinimum = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MinimumUnit(invalidMinimum));
    }

    @Test
    public void isValidMinimum() {
        // null minimum
        Assert.assertThrows(NullPointerException.class, () -> MinimumUnit.isValidMinimum(null));

        // invalid minimum
        assertFalse(MinimumUnit.isValidMinimum("")); // empty string
        assertFalse(MinimumUnit.isValidMinimum(" ")); // spaces only
        assertFalse(MinimumUnit.isValidMinimum("abcde^")); // only non-numeric characters
        assertFalse(MinimumUnit.isValidMinimum("12ab*")); // contains non-numeric characters
        assertFalse(MinimumUnit.isValidMinimum("200.2")); // numeric value with decimal separator

        // valid minimum
        assertTrue(MinimumUnit.isValidMinimum("800")); // numeric value
        assertTrue(MinimumUnit.isValidMinimum("28902375639")); // numeric value with many digits
    }
}
