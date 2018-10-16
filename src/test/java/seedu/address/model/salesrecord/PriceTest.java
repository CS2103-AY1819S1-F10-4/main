package seedu.address.model.salesrecord;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "-123";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("$6")); // contains non-number
        assertFalse(Price.isValidPrice("SGD6")); // contains alphabets
        assertFalse(Price.isValidPrice("Six Dollars")); // contains alphabets
        assertFalse(Price.isValidPrice("6.23.50")); // not a float
        assertFalse(Price.isValidPrice("-6.2")); // negative
        assertFalse(Price.isValidPrice("-$55")); // negative and contains non-number
        assertFalse(Price.isValidPrice("0")); // non-positive

        // valid price
        assertTrue(Price.isValidPrice(".5")); // valid float
        assertTrue(Price.isValidPrice("0.50")); // valid float
        assertTrue(Price.isValidPrice("10.99")); // valid float
        assertTrue(Price.isValidPrice("60")); // valid float
        assertTrue(Price.isValidPrice("60.000000")); // valid float
    }
}
