package seedu.address.model.salesrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ItemNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ItemName(null));
    }

    @Test
    public void constructor_invalidItemName_throwsIllegalArgumentException() {
        String invalidItemName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ItemName(invalidItemName));
    }

    @Test
    public void isValidItemName() {
        // null item name
        Assert.assertThrows(NullPointerException.class, () -> ItemName.isValidName(null));

        // invalid item name
        assertFalse(ItemName.isValidName("")); // empty string
        assertFalse(ItemName.isValidName(" ")); // spaces only
        assertFalse(ItemName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(ItemName.isValidName("Grilled Chicken!")); // contains non-alphanumeric characters

        // valid item name
        assertTrue(ItemName.isValidName("Fried Fish")); // alphabets only
        assertTrue(ItemName.isValidName("29128")); // numbers only
        assertTrue(ItemName.isValidName("Grilled Chicken Special 2")); // alphanumeric characters
        assertTrue(ItemName.isValidName("Steak Mania")); // with capital letters
        assertTrue(ItemName.isValidName("Pan roasted pastry rolls with herb tomato puree layers and creamy blend of "
                + "artisanal cheeses")); // long names
    }
}
