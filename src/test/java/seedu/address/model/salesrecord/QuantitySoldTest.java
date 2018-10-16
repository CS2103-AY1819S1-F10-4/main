package seedu.address.model.salesrecord;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class QuantitySoldTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new QuantitySold(null));
    }

    @Test
    public void constructor_invalidQuantitySold_throwsIllegalArgumentException() {
        String invalidQuantitySold = "100.5";
        Assert.assertThrows(IllegalArgumentException.class, () -> new QuantitySold(invalidQuantitySold));
    }

    @Test
    public void isValidQuantitySold() {
        // null quantity sold
        Assert.assertThrows(NullPointerException.class, () -> QuantitySold.isValidQuantity(null));

        // invalid quantity sold
        assertFalse(QuantitySold.isValidQuantity("")); // empty string
        assertFalse(QuantitySold.isValidQuantity(" ")); // spaces only
        assertFalse(QuantitySold.isValidQuantity("-100")); // negative integer
        assertFalse(QuantitySold.isValidQuantity("50.4")); // non-integer
        assertFalse(QuantitySold.isValidQuantity("-75.5")); // non-integer and negative
        assertFalse(QuantitySold.isValidQuantity("Seventy Four")); // non-integer
        assertFalse(QuantitySold.isValidQuantity("100-26")); // contains non-integer
        assertFalse(QuantitySold.isValidQuantity("200 and 3")); // contains non-integer
        assertFalse(QuantitySold.isValidQuantity("!()()")); // contains non-integer

        // valid quantity sold
        assertTrue(QuantitySold.isValidQuantity("0")); // non-negative integer
        assertTrue(QuantitySold.isValidQuantity("1")); // non-negative integer
        assertTrue(QuantitySold.isValidQuantity("100"));
        assertTrue(QuantitySold.isValidQuantity("405"));
    }
}
